package com.mj.skems.Security;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.mj.skems.Security.model.User;




@Repository
public interface UserRepository extends JpaRepository < User, Long > {
    User findUserByEmail(String email);
    
    //public void deleteById(Long id) ;
    @Transactional
    @Modifying
    @Query(value = "delete from users_roles where user_id like %:id% ", nativeQuery = true)
    public void deleteParentyId  (@Param("id") Long id);

    @Transactional
    @Modifying
    @Query(value = "delete from user where id like %:id% ", nativeQuery = true)
    public void deleteChildId  (@Param("id") Long id);
}