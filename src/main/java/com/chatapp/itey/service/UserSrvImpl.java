package com.chatapp.itey.service;

import com.chatapp.itey.config.security.CustomUserDetail;
import com.chatapp.itey.config.security.JwtProvider;
import com.chatapp.itey.model.entity.User;
import com.chatapp.itey.model.payload.LoginReq;
import com.chatapp.itey.model.payload.LoginResp;
import com.chatapp.itey.model.payload.UserReq;
import com.chatapp.itey.model.payload.UserResp;
import com.chatapp.itey.repo.UserRepo;
import com.chatapp.itey.utils.IteyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
public class UserSrvImpl implements UserService {
    @Autowired
    UserRepo userRepo;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    JwtProvider jwtProvider;
    @Autowired
    AuthenticationManager authenticationManager;

    @Override
    public Mono<UserResp> createUser(UserReq userReq) throws ExecutionException, InterruptedException {
        //TODO: Validate information
        String id = IteyUtils.newUUID(userReq.getUsername());
        String password = passwordEncoder.encode(userReq.getPassword());
        userReq.setPassword(password);
        User createdUser = userRepo.createUser(userReq, id);
        return Mono.just(new UserResp(createdUser, id));
    }

    @Override
    public Mono<LoginResp> loginUser(LoginReq loginReq) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginReq.getUsername(), loginReq.getPassword())
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = jwtProvider.generateToken((CustomUserDetail) authentication.getPrincipal());

            return Mono.just(new LoginResp(jwt));
        } catch (BadCredentialsException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Wrong username or password");
        }
    }

    @Override
    public Mono<UserResp> findUserById(String id) throws ExecutionException, InterruptedException {
        return Mono.just(new UserResp(userRepo.findById(id), id));
    }

    @Override
    public Mono<UserResp> findUsersByUsername(String username) throws ExecutionException, InterruptedException {
        return Mono.just(userRepo.findByUsername(username));
    }

    @Override
    public Mono<List<UserResp>> findUserByDisplayName(String displayName) throws ExecutionException, InterruptedException {
        return Mono.just(userRepo.findByDisplayName(displayName));
    }
}
