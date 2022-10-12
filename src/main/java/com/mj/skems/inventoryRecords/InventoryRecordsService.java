package com.mj.skems.inventoryRecords;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.mj.skems.Security.ShopMeUserDetails;
import com.mj.skems.Security.ShopMeUserDetailsService;
import com.mj.skems.Security.UserRepository;
import com.mj.skems.Security.model.User;

@Service
public class InventoryRecordsService {

    @Autowired
    ShopMeUserDetailsService service;
    
    @Autowired 
    InventoryRecordsRepository inventoryRecordsRepository;

                            //  Booked list
    public List<InventoryRecords> listBookedRecords(){

        String date = LocalDate.now().toString();
        List<InventoryRecords> bookedRecords = inventoryRecordsRepository.findAllByDateBooked(date);
        return bookedRecords;
    };

   public List<InventoryRecords> listRecords(){
        return inventoryRecordsRepository.findAll();
    };
    
                        //  Issued List 
    public List<InventoryRecords> listIssuedRecords(){

        return inventoryRecordsRepository.findAllByDateIssued();
    }
        
                    //  save Issuing 
    public  InventoryRecords saveIssuing(InventoryRecords inventory, long id){
        InventoryRecords records = inventoryRecordsRepository.findById(id).get();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        ShopMeUserDetails user =  (ShopMeUserDetails) service.loadUserByUsername(auth.getName());
          
        String date = LocalDate.now().toString();
        records.setDateIssued(date);
        records.setStaffIssued(user.getRegStaffNo());
        return inventoryRecordsRepository.save(records);
        

    }

                //  Save Booking
   public InventoryRecords saveBooking(InventoryRecords inventory){
        InventoryRecords records = new InventoryRecords();

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        ShopMeUserDetails user =  (ShopMeUserDetails) service.loadUserByUsername(auth.getName());
      
        records.setItem(inventory.getItem());
        records.setDateBooked(inventory.getDateBooked());
        records.setRegNo(user.getRegStaffNo()); 
        records.setFirstName(user.getFirstName()); 
        records.setLastName(user.getLastName());
           return inventoryRecordsRepository.save(records);
    
        }

            //   Save Return
    public InventoryRecords saveReturn(InventoryRecords inventory, long id){
        InventoryRecords records = inventoryRecordsRepository.findById(id).get();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        ShopMeUserDetails user =  (ShopMeUserDetails) service.loadUserByUsername(auth.getName());
          
        String date = LocalDate.now().toString();
        records.setDateReturned(date);
        records.setStaffReurned(user.getRegStaffNo());
        records.setStatus(inventory.getStatus());
        return inventoryRecordsRepository.save(records);
        
    }

}
