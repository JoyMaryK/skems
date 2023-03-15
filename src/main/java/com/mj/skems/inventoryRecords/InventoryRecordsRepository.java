package com.mj.skems.inventoryRecords;


import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface InventoryRecordsRepository extends JpaRepository<InventoryRecords, Long> {
  
     
     @Query(value = "select * from Inventory_Records where reg_no like %:regNo% and date_booked > '2022-12-17' ", nativeQuery = true)
     List<InventoryRecords> findByRegNoEquals(String regNo);

    @Query("SELECT c FROM InventoryRecords c WHERE c.dateIssued IS NOT NULL and c.dateReturned is null")
    public List<InventoryRecords>findAllByDateIssued( );

    @Query("SELECT c FROM InventoryRecords c WHERE c.dateReturned IS NOT NULL ")
    public List<InventoryRecords>findAllByDateReturned();
    

    
    @Query("SELECT c FROM InventoryRecords c WHERE c.dateBooked = :dateBooked and c.dateIssued IS NULL")
    public List<InventoryRecords>findAllByDateBooked(@Param(value = "dateBooked")String dateBooked );


   
    @Query("SELECT r from InventoryRecords r where r.regNo =:regNo and r.dateReturned is NULL")
    public Optional<InventoryRecords>findAllIssuedByRegNo(@Param(value="regNo" )String regNo);

    @Query("SELECT r from InventoryRecords r where r.email =:email and r.dateReturned is NULL and r.dateBooked is not null")
    public Optional<InventoryRecords> findAllIssuedByEmail(@Param(value="email" )String email);

    @Query("SELECT r from InventoryRecords r where r.email =:email and r.dateIssued is NULL")
    public Optional<InventoryRecords> findAllBookedByEmail(@Param(value="email" )String email);

    @Query("SELECT r from InventoryRecords r where r.item =:item and r.dateIssued is NOT NULL")
    public List<InventoryRecords> findAllByItem(@Param(value="item") String item);
   
    @Query("select distinct item from InventoryRecords")
    public List<Object[]> findDistinctItems();

    @Query("select  count(*) from InventoryRecords a where a.item =:item and status = good")
    public Integer noOfIssued(@Param(value = "item")String item );

    @Query(value = "select Count(*) from Inventory_Records where status = 'bad' and item =:item", nativeQuery = true)
    public Integer noOfBad(@Param(value = "item")String item );

    @Query(value = "select * from Inventory_Records where reg_no like %:regNo% and date_issued is not null and date_returned is null ", nativeQuery = true)
     List<InventoryRecords> searchIssuedByRegNo  (@Param("regNo") String regNo);

     @Query(value = "select * from Inventory_Records where reg_no like %:regNo% and date_booked =:dateBooked and date_issued is null", nativeQuery = true)
     List<InventoryRecords> searchBookedByRegNo  (@Param("regNo") String regNo , String dateBooked );

     @Query(value = "select * from Inventory_Records where reg_no like %:regNo% and date_returned is not null", nativeQuery = true)
     List<InventoryRecords> searchAllRecordsByRegNo  (@Param("regNo") String regNo);

}
