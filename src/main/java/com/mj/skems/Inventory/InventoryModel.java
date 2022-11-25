package com.mj.skems.Inventory;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import lombok.Setter;


@Entity
@Table(name="inventory")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

//@DynamicUpdate
@Component
public class InventoryModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private long id;


    @Column(name = "sport_name", nullable = false)
	private String sport_name;

    @Column(name = "sport_item", nullable = false)
	private String sportItem;

    @Lob
    @Column(name = "Image", columnDefinition = "LONGBLOB")
    private String image;

    private Integer available;
    private Integer totalNo;

    @Column(name="booked_no")
    private Integer bookedNo;

    @Column(name="issued_no")
    private Integer issuedNo;
    @Column(nullable = true, length = 64)
    private String photos;

}
