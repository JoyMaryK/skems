package com.mj.skems.Inventory;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

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
    
    public InventoryModel updateTotals(InventoryModel inventory,Long id, String item){
       
        Integer difference;
        InventoryModel iModel = inventoryRepository.findById(id).get();
        Integer oldTotalNo = iModel.getTotalNo();
        Integer total = inventory.getTotalNo();
        InventoryModel inventoryModel;
       
        
                //inventoryModel = inventoryRepository.findById(id).get();
               // if (iModel != null){ 
                    
                    if (total>oldTotalNo){
                        difference = total - oldTotalNo;
                    iModel.setAvailable(iModel.getAvailable() + difference) ;
                    iModel.setTotalNo(total) ;
                     }
               
            else if (total<oldTotalNo){
               difference = oldTotalNo - total;
             
                    iModel.setAvailable(iModel.getAvailable() - difference) ;
                    iModel.setTotalNo(total) ;
                   // }
                        }
                
       
        return inventoryRepository.save(iModel);
    }
  
    public InventoryModel updateBooked(InventoryModel inventory,Long id){
       
        InventoryModel imodel = inventoryRepository.findById(id).get();
        imodel.setBookedNo(inventory.getBookedNo() + 1) ;
        imodel.setBookedNo(inventory.getAvailable() - 1) ;
        return inventoryRepository.save(imodel);
    }


    public Optional<InventoryModel> findById(Long id) {
        return inventoryRepository.findById(id);
    }
    public Integer findOldTotalNo(Long id) {
        return inventoryRepository.findOldTotalNo(id);
    }

    public InventoryModel findByItem(String item) {
        return inventoryRepository.findBySportItem(item);
    }
    public Collection<InventoryModel> listSportItemsForGraph() {
        return inventoryRepository.findAll();
    }
    

}
