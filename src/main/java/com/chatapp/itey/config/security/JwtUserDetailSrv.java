package com.chatapp.itey.config.security;

import com.chatapp.itey.model.entity.User;
import com.chatapp.itey.model.payload.UserResp;
import com.chatapp.itey.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;

@Service
public class JwtUserDetailSrv implements UserDetailsService {
    @Autowired
    UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            UserResp userResp = userRepo.findUserRespByEmail(username);
            User user = userRepo.findByEmail(username);
            UserResp.currentUser().setId(userResp.getId());

            if (user == null) {
                throw new UsernameNotFoundException("User not found!!!");
            }
            return new CustomUserDetail(user, userResp);
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
