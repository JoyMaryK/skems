package com.mj.skems.Security;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.mj.skems.Security.model.User;
import com.mj.skems.inventory.InventoryRecords;
import com.mj.skems.inventory.InventoryRecordsService;

@Controller
public class MainController {
    @Autowired
    InventoryRecordsService inventoryService;
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


    @PostMapping("/book")
              
    public String saveBooking(@ModelAttribute("inventoryRecords") InventoryRecords inventoryRecords, Model model){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-mm-dd");
              
        String item = inventoryRecords.getItem();
               String dateBooked = inventoryRecords.getDateBooked();
            model.addAttribute("item", item);
            model.addAttribute("dateBooked", dateBooked);
                    inventoryService.saveBooking(inventoryRecords);
                    return "index";
                }



    @GetMapping("/booked")
    public String listRecords(Model model){
        model.addAttribute("inventoryRecords",inventoryService.listBookedRecords() );
        return"booked";
    }
            }
  
        
    

