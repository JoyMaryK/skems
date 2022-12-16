package com.mj.skems.Security;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.mj.skems.Security.model.User;
import com.mj.skems.Security.model.Role;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private  UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public User findUserByEmail(String email) {
        return userRepository.findUserByEmail(email);
    }

   
    private Object mapToUserDto(User user) {
        UserRegistrationDto userDto = new UserRegistrationDto();
       
        userDto.setFirstName(user.getFirstName());
        userDto.setLastName(user.getLastName());
        userDto.setEmail(user.getEmail());
        return userDto;
    }
    public User save(UserRegistrationDto registration) {
        User user = new User();
        user.setFirstName(registration.getFirstName());
        user.setLastName(registration.getLastName());
        user.setEmail(registration.getEmail());
        user.setPassword(passwordEncoder.encode(registration.getPassword()));
        user.setPhoneNo(registration.getPhoneNo());
        user.setRegStaffNo(registration.getRegStaffNo());
        user.setRoles(Arrays.asList(new Role(registration.getRole())));
        
        return userRepository.save(user);
    }

    // @Override
    // public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    //     User user = userRepository.findUserByEmail(email);
    //     if (user == null) {
    //         throw new UsernameNotFoundException("Invalid username or password.");
    //     }
    //     return new org.springframework.security.core.userdetails.User(user.getEmail()
    //                 , user.getPassword(),
    //                 user.getRoles().stream()
    //                         .map((role) -> new SimpleGrantedAuthority(role.getName()))
    //                         .collect(Collectors.toList())); }
            
        
    
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
      
        User user = userRepository.findUserByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("Could not find user with that email");
        }
         
        return new ShopMeUserDetails(user);
    }

   
    
    private Collection < ? extends GrantedAuthority > mapRolesToAuthorities(Collection < Role > roles) {
        return roles.stream()
            .map(role -> new SimpleGrantedAuthority(role.getName()))
            .collect(Collectors.toList());
    }


    @Override
    public List<User> listAll() {
        // TODO Auto-generated method stub
        return userRepository.findAll();
    }


    @Override
    public List<User> listByEmail(String email) {
        // TODO Auto-generated method stub
        return (List<User>) userRepository.findUserByEmail(email);
    }


    }

  
    