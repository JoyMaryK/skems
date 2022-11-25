package com.mj.skems.inventoryRecords;

import java.time.LocalDate;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.stereotype.Service;

import com.mj.skems.Inventory.InventoryModel;
import com.mj.skems.Inventory.InventoryRepository;
import com.mj.skems.Inventory.InventoryService;
import com.mj.skems.Security.ShopMeUserDetails;
import com.mj.skems.Security.ShopMeUserDetailsService;
import com.mj.skems.mail.SendMail;


@Service
public class InventoryRecordsService {

    @Autowired
    ShopMeUserDetailsService service;
    
    @Autowired 
    InventoryRecordsRepository inventoryRecordsRepository;

    @Autowired 
    InventoryRepository inventoryRepository;

    @Autowired 
    InventoryModel inventoryModel;

    @Autowired 
     InventoryService inventory_sService;


                            //  Booked list
    public List<InventoryRecords> listBookedRecords(){

        String date = LocalDate.now().toString();
        List<InventoryRecords> bookedRecords = inventoryRecordsRepository.findAllByDateBooked(date);
        return bookedRecords;
    };

   public List<InventoryRecords> listRecords(){
        return inventoryRecordsRepository.findAllByDateReturned();
    };
    
                        //  Issued List 
    public List<InventoryRecords> listIssuedRecords(){

        return inventoryRecordsRepository.findAllByDateIssued();
    }

    public List<InventoryRecords>  listIssuedRecordsForGraph(){
        
        return inventoryRecordsRepository.findAll();
       
    }
        
                    //  save Issuing 
    public  InventoryRecords saveIssuing(InventoryRecords inventory, long id){
        InventoryRecords records = inventoryRecordsRepository.findById(id).get();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        ShopMeUserDetails user =  (ShopMeUserDetails) service.loadUserByUsername(auth.getName());
          
        String staffIssuedNames[] = {user.getFirstName().toString()," ", user.getLastName().toString()}; 
        String staffNamesCombined = staffIssuedNames[0] + staffIssuedNames[1] + staffIssuedNames[2];

       String date = LocalDate.now().toString();
       String item = records.getItem();
       
       
       
       Long iD =inventoryRepository.findIdOfItem(item);
       InventoryModel inventorymodel = inventoryRepository.findById(iD).get();
        if (inventorymodel != null){ 
       // InventoryModel imodel = inventorymodel;
            // InventoryModel imodel = inventory_sService.findById(iD).get();
            inventorymodel.setBookedNo(inventorymodel.getBookedNo() - 1) ;
            inventorymodel.setIssuedNo(inventorymodel.getIssuedNo() + 1) ;
             inventoryRepository.save(inventorymodel);             
     // //send email once item is issued
     // String content = "Hello, "+"you have successfully been issued an item from the sports stores. Kindly ensure you return it to avoid consequences.";
     // SendMail.send("joyskems@gmail.com","eilb pscg pnpz spjw",
     //     "joyskems@gmail.com","ITEM ISSUED",content);
     
     records.setDateIssued(date);
       records.setStaffIssued(staffNamesCombined);
     
    }



        return inventoryRecordsRepository.save(records);
        

    }

                //  Save Booking
   public InventoryRecords saveBooking(InventoryRecords inventory, Long id){
        InventoryRecords records = new InventoryRecords();

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        ShopMeUserDetails user =  (ShopMeUserDetails) service.loadUserByUsername(auth.getName());
      
       
        
        String studentNames[] = {user.getFirstName().toString()," ", user.getLastName().toString()}; 
        String studentNamesCombined = studentNames[0] + studentNames[1] + studentNames[2];
        String item = inventory.getItem();
        // Long id = inventory.getId();
        records.setItem(item);
        records.setDateBooked(inventory.getDateBooked());
        records.setRegNo(user.getRegStaffNo()); 
        records.setFirstName(studentNamesCombined);
        records.setItemId(id); 
        // records.setLastName(user.getLastName());

        // InventoryModel imodel = inventoryRepository.findById(id).get();
        // imodel.setBookedNo(inventoryModel.getBookedNo() + 1) ;
        // inventoryRepository.save(imodel);
       // inventory_sService.updateBooked(inventoryModel, id);

           return inventoryRecordsRepository.save(records);
    
        }

            //   Save Return
    public InventoryRecords saveReturn(InventoryRecords inventory, long id){
        InventoryRecords records = inventoryRecordsRepository.findById(id).get();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        ShopMeUserDetails user =  (ShopMeUserDetails) service.loadUserByUsername(auth.getName());

        String staffIssuedNames[] = {user.getFirstName().toString()," ", user.getLastName().toString()}; 
        String staffNamesCombined = staffIssuedNames[0] + staffIssuedNames[1] + staffIssuedNames[2];
          
        String date = LocalDate.now().toString();


        String item = records.getItem();

       
          Long iD =inventoryRepository.findIdOfItem(item);
          
        InventoryModel inventorymodel = inventoryRepository.findById(iD).get();
         if (inventorymodel != null){ 
        // InventoryModel imodel = inventorymodel;
             // InventoryModel imodel = inventory_sService.findById(iD).get();
             inventorymodel.setAvailable(inventorymodel.getAvailable() + 1) ;
             inventorymodel.setIssuedNo(inventorymodel.getIssuedNo() - 1) ;
              inventoryRepository.save(inventorymodel);             
    //   //send email once item is issued
    //   String content = "Hello, you have successfully returned "+item +"from the sports stores. Thank you! ";
    //   SendMail.send("joyskems@gmail.com","eilb pscg pnpz spjw",
    //       "joyskems@gmail.com","ITEM ISSUED",content);

    records.setDateReturned(date);
    records.setStaffReurned(staffNamesCombined);
    records.setStatus(inventory.getStatus());
       
        }
        return inventoryRecordsRepository.save(records);
        
    }

    public List<InventoryRecords> listAllUnreturned() {
        return null;
    }

    public List<InventoryRecords> findByRegNoEquals(String regNo) {

        return inventoryRecordsRepository.findByRegNoEquals(regNo);
    }

}
