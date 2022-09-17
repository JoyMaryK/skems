package com.mj.skems.inventory;

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
  
    public List<InventoryRecords>findAllByDateIssued(String dateIssued );
    
    @Transactional
    @Modifying
    @Query("update InventoryRecords i set i.dateIssued = :dateIssued, i.staffIssued = :staffIssued where i.regNo = :regNo")
    void addIssuing(@Param(value = "regNo") String regNo,
     @Param(value = "staffIssued") String staffIssued, @Param(value = "dateIssued") String dateIssued);


    // @Query(value= "SELECT * FROM inventory_records WHERE date_issued is null and date_booked is not null", nativeQuery = true)
    @Query("SELECT c FROM InventoryRecords c WHERE c.dateBooked = :dateBooked and c.dateIssued IS NULL")
    public List<InventoryRecords>findAllByDateBooked(@Param(value = "dateBooked")String dateBooked );
}
