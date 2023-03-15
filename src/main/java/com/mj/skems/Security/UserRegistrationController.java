package com.mj.skems.Security;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mj.skems.Security.model.StaffDto;
import com.mj.skems.Security.model.User;

@Controller
@RequestMapping("/registration")
public class UserRegistrationController {

    @Autowired
    private UserService userService;

    @ModelAttribute("user")
    public UserRegistrationDto userRegistrationDto() {
        return new UserRegistrationDto();
    }

    @GetMapping
    public String showRegistrationForm(Model model) {
        return "registration";
    }

    @PostMapping
    public String registerUserAccount(@ModelAttribute("user") @Valid UserRegistrationDto userDto,
        BindingResult result ) {

        User existing = userService.findUserByEmail(userDto.getEmail());      //
        if (existing != null) {
            result.rejectValue("email", null, "There is already an account registered with that email");
        }

        if (result.hasErrors()) {
            return "registration";
        }

        userService.save(userDto);
        // return "redirect:/registration?success";
        return "redirect:/";
    }

    @PostMapping("/registerStaff")
    public String registerStaffUserAccount(@ModelAttribute("user") @Valid StaffDto userDto, Model model,
        BindingResult result ) {

        User existing = userService.findUserByEmail(userDto.getEmail());      //
        if (existing != null) {
            result.rejectValue("email", null, "There is already an account registered with that email");
        }

        if (result.hasErrors()) {
            return "registration";
        }
        String firstName = userDto.getFirstName();
        String lastName = userDto.getLastName();
        String phoneNo = userDto.getPhoneNo();
        String regStaffNo = userDto.getRegStaffNo();
        String email = userDto.getEmail();
        String password = userDto.getPassword();
        userService.saveStaff(userDto);

        model.addAttribute("email",email);
        model.addAttribute("firstName",firstName);
        model.addAttribute("lastName",lastName);
        model.addAttribute("phoneNo",phoneNo);
        model.addAttribute("password",password);
        model.addAttribute("regStaffNo",regStaffNo);
        // return "redirect:/registration?success";
        return "redirect:/";
    }
    @GetMapping("/registerStaff")
    public String staffReg(Model model){
        
        return"staffRegistration";
    }
}