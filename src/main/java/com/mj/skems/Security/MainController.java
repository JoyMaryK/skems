package com.mj.skems.Security;

import java.util.Optional;

import javax.persistence.Id;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.mj.skems.Security.model.User;

@Controller
public class MainController {

    @Autowired
 UserService service;

 @Autowired
 UserRepository userRepository;
    @GetMapping("/")
    public String root(@AuthenticationPrincipal ShopMeUserDetails userDetails,
                                Model model) {
            String userEmail = userDetails.getUsername();
            User user = service.findUserByEmail(userEmail);
 
            model.addAttribute("user", user);
            model.addAttribute("pageTitle", "Account Details");

        return "index";
    }

    @GetMapping("/index")
    public String index() {
        return "index";
    }

    @GetMapping("/login")
    public String login(Model model) {
        return "login";
    }

    // @GetMapping("/user")
    // public String userIndex() {
    //     return "user/index";
    // }

    @GetMapping("/photo-detail")
    public String photoDetail() {
        return "photo-detail";
    }

    @GetMapping("/contact")
    public String contact(){
        return "contact";
    }
   
    @GetMapping("/about")
    public String about(){
        return "about";
    }
    @GetMapping("/user")
    public String loggedInUser
        (@AuthenticationPrincipal ShopMeUserDetails userDetails,
        Model model) {
          String userEmail = userDetails.getUsername();
            User user = service.findUserByEmail(userEmail);

            model.addAttribute("user", user);
            model.addAttribute("pageTitle", "Account Details");

            return "index";
                }

  
}
    

