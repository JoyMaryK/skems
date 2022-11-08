package com.mj.skems.Inventory;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mj.skems.inventoryRecords.InventoryRecordsRepository;

@Service
public class InventoryService {
    
    
    @Autowired 
    InventoryRepository inventoryRepository;

    @Autowired
    InventoryRecordsRepository inventoryRecordsRepository;

    
   public List<InventoryModel> listInventory(){
        return inventoryRepository.findAll();
    }


    public InventoryModel updateIssued(InventoryModel inventory, Long id){
            InventoryModel iModel = inventoryRepository.findById(id).get();
            Integer soccerIssued = inventoryRecordsRepository.noOfIssued("soccer ball");

            iModel.setIssuedNo(soccerIssued);
            return inventoryRepository.save(iModel);

    }
    
    public InventoryModel updateTotals(InventoryModel inventory,Long id){
       
        InventoryModel imodel = inventoryRepository.findById(id).get();
        imodel.setTotalNo(inventory.getTotalNo()) ;
        
        return inventoryRepository.save(imodel);
    }
  
    
}
