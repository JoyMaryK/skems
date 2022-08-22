package com.mj.skems.Security;

import javax.persistence.Id;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.mj.skems.Security.model.ShopMeUserDetails;
import com.mj.skems.Security.model.User;

@Controller
public class MainController {

    @GetMapping("/")
    public String root() {
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

    @GetMapping("/user")
    public String userIndex() {
        return "user/index";
    }

   

    @GetMapping("/contact")
    public String contact(){
        return "contact";
    }
   
    @GetMapping("/about")
    public String about(){
        return "about";
    }
}
    

