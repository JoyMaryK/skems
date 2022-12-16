package com.mj.skems.inventoryRecords;



import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.springframework.format.annotation.DateTimeFormat;

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

  @NotNull
   //@Pattern(regexp = "(0?[1-9]|1[012])/(0?[1-9]|[12][0-9]|3[01])/((?:19|20)[0-9][0-9])",message = "use the calender to pick a date")
    private String dateBooked;

    private String staffIssued;
    private String staffReurned;
    private String firstName;
    private String lastName;
    private Long ItemId;
    private String email;
    
    
    private String pwd;

}
