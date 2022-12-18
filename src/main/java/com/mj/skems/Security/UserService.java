package com.mj.skems.Security;



import java.util.List;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.mj.skems.Security.model.StaffDto;
import com.mj.skems.Security.model.User;


@Service
public interface UserService extends UserDetailsService {

    
    User save(UserRegistrationDto registration);
    User saveStaff(StaffDto registration);
    User findUserByEmail(String email);

    List<User> listAll();

    List<User> listByEmail(String email);
    // List<UserRegistrationDto> findAllUsers();
}
