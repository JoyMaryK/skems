package com.mj.skems.inventory;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository

public interface InventoryRecordsRepository extends JpaRepository<InventoryRecords, Long> {
    public List<InventoryRecords>findAllByDateBooked(String dateBooked );
    
}
