package com.mj.skems.inventoryRecords;

import java.time.LocalDate;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
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
