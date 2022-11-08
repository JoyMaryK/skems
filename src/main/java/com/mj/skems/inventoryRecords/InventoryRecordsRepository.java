package com.mj.skems.inventoryRecords;


import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface InventoryRecordsRepository extends JpaRepository<InventoryRecords, Long> {
  


    @Query("SELECT c FROM InventoryRecords c WHERE c.dateIssued IS NOT NULL and c.dateReturned is null")
    public List<InventoryRecords>findAllByDateIssued( );

    @Query("SELECT c FROM InventoryRecords c WHERE c.dateReturned IS NOT NULL ")
    public List<InventoryRecords>findAllByDateReturned();
    

    
    @Query("SELECT c FROM InventoryRecords c WHERE c.dateBooked = :dateBooked and c.dateIssued IS NULL")
    public List<InventoryRecords>findAllByDateBooked(@Param(value = "dateBooked")String dateBooked );


   
    @Query("SELECT r from InventoryRecords r where r.regNo =:regNo and r.dateReturned is NULL")
    public Optional<InventoryRecords>findAllIssuedByRegNo(@Param(value="regNo" )String regNo);

    @Query("SELECT r from InventoryRecords r where r.item =:item and r.dateIssued is NOT NULL")
    public List<InventoryRecords> findAllByItem(@Param(value="item") String item);
   
    @Query("select distinct item from InventoryRecords")
    public List<Object[]> findDistinctItems();

    @Query("select  count(*) from InventoryRecords a where a.item =:item and a.dateReturned is null and a.dateIssued is not null")
    public Integer noOfIssued(@Param(value = "item")String item );
}
