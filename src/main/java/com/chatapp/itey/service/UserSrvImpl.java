package com.chatapp.itey.service;

import com.chatapp.itey.config.security.CustomUserDetail;
import com.chatapp.itey.config.security.JwtProvider;
import com.chatapp.itey.model.entity.User;
import com.chatapp.itey.model.payload.LoginReq;
import com.chatapp.itey.model.payload.LoginResp;
import com.chatapp.itey.model.payload.UserReq;
import com.chatapp.itey.model.payload.UserResp;
import com.chatapp.itey.repo.FriendRepo;
import com.chatapp.itey.repo.UserRepo;
import com.chatapp.itey.utils.IteyUtils;
import com.chatapp.itey.utils.Validator;
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

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
public class UserSrvImpl implements UserService {
    @Autowired
    UserRepo userRepo;
    @Autowired
    FriendRepo friendRepo;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    JwtProvider jwtProvider;
    @Autowired
    AuthenticationManager authenticationManager;

    @Override
    public Mono<UserResp> createUser(UserReq userReq) throws ExecutionException, InterruptedException {
        Validator.userSignInInformationValidate(userReq);
        String id = IteyUtils.newUUID(userReq.getEmail());
        String password = passwordEncoder.encode(userReq.getPassword());
        userReq.setPassword(password);
        User createdUser = userRepo.createUser(userReq, id);
        return Mono.just(new UserResp(createdUser, id));
    }

    @Override
    public Mono<UserResp> editUser(UserReq userReq) {
        String currentUserId = UserResp.currentUser().getId();
        Validator.userSignInInformationValidate(userReq);
        User editedUser = userRepo.updateUser(userReq);
        return Mono.just(new UserResp(editedUser, currentUserId));
    }

    @Override
    public Mono<LoginResp> loginUser(LoginReq loginReq) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginReq.getUsername(), loginReq.getPassword())
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = jwtProvider.generateToken((CustomUserDetail) authentication.getPrincipal());
            UserResp user = userRepo.findUserRespByEmail(loginReq.getUsername());
            return Mono.just(new LoginResp(user, jwt));
        } catch (BadCredentialsException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Wrong username or password");
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
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

    @Override
    public Mono<List<UserResp>> findUserNotFriendByDisplayName(String displayName) throws ExecutionException, InterruptedException {
        List<UserResp> result = new ArrayList<>();
        String currentUserId = UserResp.currentUser().getId();
        User currentUser = userRepo.findById(currentUserId);
        List<UserResp> users = userRepo.getAll().stream()
                .filter((value) -> value.getDisplayName().toLowerCase().contains(displayName.toLowerCase()) && !value.getId().equals(currentUserId) )
                .toList();
        List<String> friendIds = currentUser.getFriendIds();
        users.forEach(value -> {
            if(!friendIds.contains(value.getId())) {
                result.add(value);
            }
        });
        return Mono.just(result);
    }

    @Override
    public Mono<List<UserResp>> getAllUsers() {
        return Mono.just(userRepo.getAll());
    }
}
