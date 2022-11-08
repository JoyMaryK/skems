package com.mj.skems.Inventory;

import org.springframework.stereotype.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

@Repository
public interface InventoryRepository extends JpaRepository<InventoryModel, Long>{
    
    
    public InventoryModel findBySportItem(String item);
}
