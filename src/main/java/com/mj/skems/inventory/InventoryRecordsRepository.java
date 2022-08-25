package com.mj.skems.inventory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository

public interface InventoryRecordsRepository extends JpaRepository<InventoryRecords, Long> {
    
}
