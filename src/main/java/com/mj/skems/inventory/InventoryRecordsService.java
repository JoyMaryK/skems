package com.mj.skems.inventory;

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


    public List<InventoryRecords> listBookedRecords(){
        String date = LocalDate.now().toString();
        List<InventoryRecords> bookedRecords = inventoryRecordsRepository.findAllByDateBooked(date);
        return bookedRecords;
    };

   public List<InventoryRecords> listRecords(){
        return inventoryRecordsRepository.findAll();
    };
    
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

}
