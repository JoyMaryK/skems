package com.mj.skems.inventoryRecords;



import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import lombok.Setter;

@Entity
@Table(name="inventoryRecords")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

public class InventoryRecords {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String regNo;

    
    private String status;
    private String item;
    private String dateIssued;
    private String dateReturned;
   // private Date dateCreated;
    private String dateBooked;
    private String staffIssued;
    private String staffReurned;
    private String firstName;
    private String lastName;

    


}
