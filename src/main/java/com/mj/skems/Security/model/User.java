package com.mj.skems.Security.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity

@Table(uniqueConstraints = @UniqueConstraint(columnNames = "email"))
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private Long phoneNo;
    private String regStaffNo;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
        name = "users_roles",
        joinColumns = @JoinColumn(
            name = "user_id", referencedColumnName = "id"),
        inverseJoinColumns = @JoinColumn(
            name = "role_id", referencedColumnName = "id"))
    private Collection < Role > roles;

    public User() {}

    public User(String firstName, String lastName, String email, String password, Long phoneNo, String regStaffNo) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.phoneNo = phoneNo;
        this.regStaffNo = regStaffNo;
    }

    public User(String firstName, String lastName, String email, String password, Collection < Role > roles,  Long phoneNo, String regStaffNo) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.roles = roles;
        this.phoneNo = phoneNo;
        this.regStaffNo = regStaffNo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    
    
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    public void setPhoneNo(Long phoneNo) {
        this.phoneNo = phoneNo;
    }
    public Long getPhoneNo() {
        return phoneNo;
    }

    public String getRegStaffNo() {
        return regStaffNo;
    }
    public void setRegStaffNo(String regStaffNo) {
        this.regStaffNo = regStaffNo;
    }

    public Collection < Role > getRoles() {
        return roles;
    }

    public void setRoles(Collection < Role > roles) {
        this.roles = roles;
    }

    @Override
    public String toString() {
        return "User{" +
            "id=" + id +
            ", firstName='" + firstName + '\'' +
            ", lastName='" + lastName + '\'' +
            ", email='" + email + '\'' +
            ", password='" + "*********" + '\'' +
            ", roles=" + roles + '\''+
            ", phoneNo='" + phoneNo+ '\''+
            ", regStaffNo=" + regStaffNo + 
                        '}';
    }

   }