package com.mj.skems.Security;



import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.http.HttpClient.Redirect;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.zip.DataFormatException;
import java.util.zip.Inflater;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.view.RedirectView;
import org.thymeleaf.util.StringUtils;

import com.lowagie.text.DocumentException;
import com.mj.ToPDF.Pdf;
import com.mj.skems.Inventory.FileUploadUtil;
import com.mj.skems.Inventory.ImgUtil;
import com.mj.skems.Inventory.InventoryModel;
import com.mj.skems.Inventory.InventoryRepository;
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
     BCryptPasswordEncoder passwordEncoder;
 

 @Autowired
 UserRepository userRepository;

 @Autowired
 InventoryRepository inventoryRepository;
    @GetMapping("/")
    public String root(@AuthenticationPrincipal ShopMeUserDetails userDetails, InventoryModel iModel,
                                Model model) {
            String userEmail = userDetails.getUsername();
            User user = service.findUserByEmail(userEmail);

 
            model.addAttribute("user", user);
            model.addAttribute("pageTitle", "Account Details");
            model.addAttribute("inventory",inventory_sService.listInventory() );
            model.addAttribute("imgUtil",new ImgUtil());
         
            
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
    // @GetMapping("/date")
    // public String date(){
    //     return "date";
    // }
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
              
    public String saveBooking(@ModelAttribute("inventoryRecords")  InventoryRecords inventoryRecords,  Model model){
       
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        ShopMeUserDetails user =  (ShopMeUserDetails) service.loadUserByUsername(auth.getName());
              
        

        String item = inventoryRecords.getItem();
        Long id = inventoryRecords.getId();
        String dateBooked = inventoryRecords.getDateBooked();
            model.addAttribute("item", item);
            model.addAttribute("dateBooked", dateBooked);
            model.addAttribute("id", id);
        
            if (dateBooked.isBlank()){
                        return "date";
            }
            
       
                //change data on Inventory
            InventoryModel inventoryModel = inventory_sService.findById(id).get();
            if (inventoryModel != null){ 
                InventoryModel imodel = inventoryRepository.findById(id).get();
                imodel.setBookedNo(inventoryModel.getBookedNo() + 1) ;
                imodel.setAvailable(inventoryModel.getAvailable() - 1) ;
                 inventoryRepository.save(imodel);}
           
            //send email once item is booked
            String email = user.getEmail();
            String content = "Hello "+ user.getFirstName() +", you have successfully booked a "+ item +" .Kindly pick it up within the next 24hrs. Carry your school ID Card";
            SendMail.send("joyskems@gmail.com","eilb pscg pnpz spjw",
            email,"ITEM BOOKED",content);  

                    inventoryService.saveBooking(inventoryRecords, id);
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
        String email = inventoryRecords.getEmail();
        // String staffIssued = user.getRegStaffNo();
        String staffIssued = staffNamesCombined;
        String dateIssued = inventoryRecords.getDateIssued();
        long id = inventoryRecords.getId();
       // String item = inventoryRecords.getItem();
     
    
         
        model.addAttribute("dateIssued", dateIssued);
        model.addAttribute("staffIssued", staffIssued);
        model.addAttribute("regNo", regNo);
        model.addAttribute("id", id);
        model.addAttribute("email", email);
            UserDetails user2 =  service.loadUserByUsername(email);
            String pwd = inventoryRecords.getPwd();

        model.addAttribute("pwd", pwd);
      
            if (passwordEncoder.matches(pwd, user2.getPassword()) == false ){


                
                return "issueError";
        
               }

                    inventoryService.saveIssuing(inventoryRecords, id);
                    return "redirect:booked";
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

               String email = inventoryRecords.getEmail();
               
            model.addAttribute("dateReturned", dateReturned);
            model.addAttribute("staffReurned", staffReurned);
            model.addAttribute("status", status);
            model.addAttribute("id", id);
            model.addAttribute("email", email);
            UserDetails user2 =  service.loadUserByUsername(email);
            String pwd = inventoryRecords.getPwd();

        model.addAttribute("pwd", pwd);
      
            if (passwordEncoder.matches(pwd, user2.getPassword()) == false ){


                
                return "returnError";
        
               }

                    inventoryService.saveReturn(inventoryRecords, id);
                    return "redirect:issued";
                }


    @GetMapping("/records")
    public String allRecords(Model model){
        model.addAttribute("inventoryRecords",inventoryService.listRecords() );
        return"records";
    }

    @GetMapping("/items")
    public String allItems(Model model){
        model.addAttribute("inventory",inventory_sService.listInventory() );
        return"items";
    }

    @GetMapping("/barChart")
	public String getRecordGraph(Model model) {	
		
    List<String> sportNameList= inventory_sService.listSportItemsForGraph().stream().map(x->x.getSportItem()).collect(Collectors.toList());
    List<Integer> sportIssuedList= inventory_sService.listSportItemsForGraph().stream().map(x->x.getIssuedNo()).collect(Collectors.toList());
    
	model.addAttribute("name", sportNameList);
	model.addAttribute("age", sportIssuedList);
	return "barChart";
	
	}

    @PostMapping("/totals")          
    public String saveNewTotals(@ModelAttribute("inventory") InventoryModel inventoryModel, Model model){
        // DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-mm-dd");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        ShopMeUserDetails user =  (ShopMeUserDetails) service.loadUserByUsername(auth.getName());

            
        Integer total = inventoryModel.getTotalNo();
        Long id = inventoryModel.getId();
        String item = inventoryModel.getSportItem();
            model.addAttribute("totalNo", total);
            model.addAttribute("item", item);

         

                    inventory_sService.updateTotals(inventoryModel, id, item);
                    return "redirect:items";
                }
               
    
                @GetMapping("/pdf")
                public void  Pdf(HttpServletResponse response) throws DocumentException, IOException {
                    response.setContentType("application/pdf");
                    DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
                    String currentDateTime = dateFormatter.format(new Date());
                     
                    String headerKey = "Content-Disposition";
                    String headerValue = "Content-Disposition; filename=defaulters_" + currentDateTime + ".pdf";
                    response.setHeader(headerKey, headerValue);
                     
                    List<InventoryRecords> listUsers = inventoryService.listIssuedRecords();
                     
                    Pdf exporter = new Pdf(listUsers);
                    exporter.export(response);
                    
    
                     
                }
               @GetMapping("/index2")
               public String show(){
                return "index2";
               } 
                @PostMapping("/item")
                public String saveItem( @RequestParam("name")String name,
                @RequestParam("item")String item, @RequestParam("total")Integer total, @RequestParam("file")MultipartFile file ) {
                     
                     inventory_sService.saveNewItem(name, item, total, file);
                    return   "redirect:items";
                          }
                
            

            @GetMapping("/searchIssued")
            public String listFoundRecords(@Param("regNo")String regNo ,Model model){
              
                if (regNo != null){
                
                model.addAttribute("inventoryRecords", inventoryService.listSearchResultsIssued(regNo));
                }
                else {
                List<InventoryRecords> list = inventoryService.listIssuedRecords();
                model.addAttribute("inventoryRecords",inventoryService.listIssuedRecords());
                }
                return"issued";
            }
            @GetMapping("/searchBooked")
            public String listFoundBookedRecords(@Param("regNo")String regNo ,Model model){
              
                if (regNo != null){
                
                model.addAttribute("inventoryRecords", inventoryService.listSearchResultsBooked(regNo));
                }
                else {
                List<InventoryRecords> list = inventoryService.listIssuedRecords();
                model.addAttribute("inventoryRecords",inventoryService.listBookedRecords());
                }
                return"booked";
            }


          
    @GetMapping("/studentIssued")
    public String listIssuedStudentRecords(Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        ShopMeUserDetails user =  (ShopMeUserDetails) service.loadUserByUsername(auth.getName());

        model.addAttribute("inventoryRecords",inventoryService.findByRegNoEquals(user.getRegStaffNo()) );
        return"studentAccount";
    }
  
    @GetMapping("/adminUsers")
    public String listUsers(Model model){
        model.addAttribute("users",service.listAll() );
        return"accountAdmin";
    }
    @GetMapping("/studentHelp")
    public String studentHelp(){
        
        return"studentFAQ";
    }
    @GetMapping("/staffHelp")
    public String staffHelp(){
        
        return"staffFAQ";
    }
    @GetMapping("/searchUsers")
            public String listSearchedUser(@Param("email")String email ,Model model){
              
                if (email != null){
                
                model.addAttribute("users", service.findUserByEmail(email));
                }
                else {
                List<User> list = service.listAll();
                model.addAttribute("users",list);
                }
                return"accountAdmin";
            }

}
  
        
    

