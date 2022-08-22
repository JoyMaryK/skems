package com.mj.skems.Security;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.mj.skems.Security.model.User;



public interface UserService extends UserDetailsService {

    
    User save(UserRegistrationDto registration);

    User findUserByEmail(String email);

    // List<UserRegistrationDto> findAllUsers();
}
