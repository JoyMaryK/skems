package com.mj.skems.Security;



import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import org.springframework.web.bind.annotation.PostMapping;


import com.mj.skems.Inventory.InventoryService;
import com.mj.skems.Security.model.User;
import com.mj.skems.inventoryRecords.InventoryRecords;
import com.mj.skems.inventoryRecords.InventoryRecordsService;
import com.mj.skems.mail.SendMail;

@Controller
public class MainController {
    @Autowired
    InventoryRecordsService inventoryService;
    @Autowired
     UserService service;

     @Autowired 
     InventoryService inventory_sService;

     @Autowired
     SendMail mail;

 @Autowired
 UserRepository userRepository;
    @GetMapping("/")
    public String root(@AuthenticationPrincipal ShopMeUserDetails userDetails,
                                Model model) {
            String userEmail = userDetails.getUsername();
            User user = service.findUserByEmail(userEmail);
 
            model.addAttribute("user", user);
            model.addAttribute("pageTitle", "Account Details");
            model.addAttribute("inventory",inventory_sService.listInventory() );
        
            return "index";
    }

    @GetMapping("/index")
    public String index(Model model) {
        model.addAttribute("inventory",inventory_sService.listInventory() );
        
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
            model.addAttribute("inventory",inventory_sService.listInventory() );
        
            return "index";
                }


    @PostMapping("/book")
              
    public String saveBooking(@ModelAttribute("inventoryRecords") InventoryRecords inventoryRecords, Model model){
       
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        ShopMeUserDetails user =  (ShopMeUserDetails) service.loadUserByUsername(auth.getName());
              
        String item = inventoryRecords.getItem();
               String dateBooked = inventoryRecords.getDateBooked();
            model.addAttribute("item", item);
            model.addAttribute("dateBooked", dateBooked);
                //send email once item is booked

                String content = "Hello "+ user.getFirstName() +", you have successfully booked a"+ item +" . Kindly pick it up within the next 24hrs.";
            SendMail.send("joyskems@gmail.com","xeo cjac blqd ewqlt",
            "joyskems@gmail.com","ITEM BOOKED",content);  


                    inventoryService.saveBooking(inventoryRecords);
                    return "redirect:index";
                }



    @GetMapping("/booked")
    public String listRecords(Model model){
        model.addAttribute("inventoryRecords",inventoryService.listBookedRecords() );
        return"booked";
    }
           
    @PostMapping("/issued")
    public String addIssuedItemDetails(@ModelAttribute("inventoryRecords") InventoryRecords inventoryRecords,  Model model ){
       
       //get details of the logged in user to get the staff member's staff id
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        ShopMeUserDetails user =  (ShopMeUserDetails) service.loadUserByUsername(auth.getName());

            String staffIssuedNames[] = {user.getFirstName().toString(),user.getLastName().toString()}; 
            String staffNamesCombined = staffIssuedNames[0] + staffIssuedNames[1];

        String regNo = inventoryRecords.getRegNo();
        
        // String staffIssued = user.getRegStaffNo();
        String staffIssued = staffNamesCombined;
        String dateIssued = inventoryRecords.getDateIssued();
        long id = inventoryRecords.getId();
        String item = inventoryRecords.getItem();
        model.addAttribute("dateIssued", dateIssued);
        model.addAttribute("staffIssued", staffIssued);
        model.addAttribute("regNo", regNo);
        model.addAttribute("id", id);
        
                
        //send email once item is issued
        String content = "Hello, "+"you have successfully been issued an item from the sports stores. Kindly ensure you return it to avoid consequences.";
        SendMail.send("joyskems@gmail.com","xeo cjac blqd ewqlt",
            "joyskems@gmail.com","ITEM ISSUED",content);
        
        inventoryService.saveIssuing(inventoryRecords, id);

       return "redirect:booked" ;
       
    }

    @GetMapping("/issued")
    public String listIssuedRecords(Model model){
        model.addAttribute("inventoryRecords",inventoryService.listIssuedRecords() );
        return"issued";
    }

  
    @PostMapping("/returned")
              
    public String saveItemReturn(@ModelAttribute("inventoryRecords") InventoryRecords inventoryRecords, Model model){
        // DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-mm-dd");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        ShopMeUserDetails user =  (ShopMeUserDetails) service.loadUserByUsername(auth.getName());


        String dateReturned = inventoryRecords.getDateReturned();
               String staffReurned = user.getRegStaffNo().toString();
               String status = inventoryRecords.getStatus();
               long id = inventoryRecords.getId();
            model.addAttribute("dateReturned", dateReturned);
            model.addAttribute("staffReurned", staffReurned);
            model.addAttribute("status", status);
            model.addAttribute("id", id);

                    inventoryService.saveReturn(inventoryRecords, id);
                    return "redirect:issued";
                }


    @GetMapping("/records")
    public String allRecords(Model model){
        model.addAttribute("inventoryRecords",inventoryService.listRecords() );
        return"records";
    }

}
  
        
    

