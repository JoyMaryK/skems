package com.mj.skems.Inventory;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InventoryService {
    
    
    @Autowired 
    InventoryRepository inventoryRepository;

    
   public List<InventoryModel> listInventory(){
        return inventoryRepository.findAll();
    };
    
  
    
}
