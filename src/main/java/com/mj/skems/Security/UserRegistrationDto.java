package com.mj.skems.Security;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;




@FieldMatch.List({
    @FieldMatch(first = "password", second = "confirmPassword", message = "The password fields must match"),
   
})
public class UserRegistrationDto {

    

    @NotEmpty
    private String firstName;

    @NotEmpty
    private String lastName;

    @NotEmpty
    private String password;

    @NotEmpty
    private String confirmPassword;

    @Email
    @NotEmpty
    private String email;

    @Email
    
    private String confirmEmail;

    // @AssertTrue
    // private Boolean terms;

    @NotEmpty
    private String role;


    private Long phoneNo;

    @NotEmpty
    private String regStaffNo;

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getConfirmEmail() {
        return confirmEmail;
    }

    public void setConfirmEmail(String confirmEmail) {
        this.confirmEmail = confirmEmail;
    }

    // public Boolean getTerms() {
    //     return terms;
    // }

    // public void setTerms(Boolean terms) {
    //     this.terms = terms;
    // }

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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

}