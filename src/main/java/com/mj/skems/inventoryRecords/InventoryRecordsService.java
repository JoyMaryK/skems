package com.mj.skems.inventoryRecords;

import java.time.LocalDate;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.stereotype.Service;

import com.mj.skems.Security.ShopMeUserDetails;
import com.mj.skems.Security.ShopMeUserDetailsService;


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
        records.setDateIssued(date);
        records.setStaffIssued(staffNamesCombined);
        return inventoryRecordsRepository.save(records);
        

    }

                //  Save Booking
   public InventoryRecords saveBooking(InventoryRecords inventory){
        InventoryRecords records = new InventoryRecords();

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        ShopMeUserDetails user =  (ShopMeUserDetails) service.loadUserByUsername(auth.getName());

        String studentNames[] = {user.getFirstName().toString()," ", user.getLastName().toString()}; 
        String studentNamesCombined = studentNames[0] + studentNames[1] + studentNames[2];
      
        records.setItem(inventory.getItem());
        records.setDateBooked(inventory.getDateBooked());
        records.setRegNo(user.getRegStaffNo()); 
        records.setFirstName(studentNamesCombined); 
        // records.setLastName(user.getLastName());
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
        records.setDateReturned(date);
        records.setStaffReurned(staffNamesCombined);
        records.setStatus(inventory.getStatus());
        return inventoryRecordsRepository.save(records);
        
    }

}
