package com.mj.skems.inventory;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

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
    private Date dateIssued;
    private Date dateReturned;
    private Date dateCreated;
    private Date dateBooked;
    private String staffIssued;
    private String staffReurned;


    


}
