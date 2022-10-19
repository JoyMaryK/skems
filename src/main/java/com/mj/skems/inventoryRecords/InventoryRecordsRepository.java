package com.mj.skems.inventoryRecords;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface InventoryRecordsRepository extends JpaRepository<InventoryRecords, Long> {
  


    @Query("SELECT c FROM InventoryRecords c WHERE c.dateIssued IS NOT NULL and c.dateReturned is null")
    public List<InventoryRecords>findAllByDateIssued( );

    @Query("SELECT c FROM InventoryRecords c WHERE c.dateReturned IS NOT NULL ")
    public List<InventoryRecords>findAllByDateReturned();
    
    // @Transactional
    // @Modifying
    // @Query("update InventoryRecords i set i.dateIssued = :dateIssued, i.staffIssued = :staffIssued where i.regNo = :regNo and dateIssued is NULL")
    // //@Query("INSERT INTO InventoryRecords (dateIssued, staffIssued) VALUES (:dateIssued , :staffIssued) where regNo =:regNo and dateIssued is NULL ")
    // void addIssuing(@Param(value = "regNo") String regNo,
    //  @Param(value = "staffIssued") String staffIssued, @Param(value = "dateIssued") String dateIssued);


    // @Query(value= "SELECT * FROM inventory_records WHERE date_issued is null and date_booked is not null", nativeQuery = true)
    
    @Query("SELECT c FROM InventoryRecords c WHERE c.dateBooked = :dateBooked and c.dateIssued IS NULL")
    public List<InventoryRecords>findAllByDateBooked(@Param(value = "dateBooked")String dateBooked );

//     @Transactional
//     @Modifying
//    // @Query("update InventoryRecords j set j.dateReturned = :dateReturned, j.staffReurned = :staffReturned ,j.status =:status where j.id =:id")
//    @Query("INSERT INTO InventoryRecords(dateReturned, staffReurned, status) VALUES (:dateReturned , :staffReturned, :status) where id =:id ")
//     void addItemReturn(@Param(value = "staffReturned") String staffReturned,
//      @Param(value = "dateReturned") String dateReturned, @Param(value="status") String status, @Param(value="id") long id);

}
