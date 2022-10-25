package com.mj.skems.Security;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mj.skems.Security.model.User;




@Repository
public interface UserRepository extends JpaRepository < User, Long > {
    User findUserByEmail(String email);
  
}