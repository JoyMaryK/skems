package com.mj.skems.inventory;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.mj.skems.Security.ShopMeUserDetailsService;
import com.mj.skems.Security.UserRepository;
import com.mj.skems.Security.model.User;

@Service
public class InventoryRecordsService {

    @Autowired
    ShopMeUserDetailsService service;
    @Autowired 
    InventoryRecordsRepository inventoryRecordsRepository;

   public InventoryRecords saveBooking(InventoryRecords inventory){
        InventoryRecords records = new InventoryRecords();
        // User user = service.loadUserByUsername(email)
        Date date = new Date();
        records.setDateBooked(date);
        records.setRegNo("S13/03140");  //needs to be got from user details
        // records.setRegNo();

           return inventoryRecordsRepository.save(records);
    }
}
