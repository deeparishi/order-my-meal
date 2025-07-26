package com.foodapp.idp.security;

import com.foodapp.common.utils.AppMessages;
import com.foodapp.idp.exception.NotFound;
import com.foodapp.idp.model.User;
import com.foodapp.idp.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDetailService implements UserDetailsService {

    @Autowired
    UserRepo userRepo;

    @Override
    public User loadUserByUsername(String emailOrMobileNumber) throws UsernameNotFoundException {
        Optional<User> user = userRepo.findByEmailOrMobileNumber(emailOrMobileNumber);
        return user.orElseThrow(() -> new NotFound(AppMessages.USER_NOT_FOUND));
    }

}
