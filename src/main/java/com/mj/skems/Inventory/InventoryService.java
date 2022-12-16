package com.mj.skems.Inventory;

import java.io.IOException;
import java.util.Base64;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.mj.skems.inventoryRecords.InventoryRecordsRepository;

@Service
public class InventoryService {
    
    
    @Autowired 
    InventoryRepository inventoryRepository;

    @Autowired
    InventoryRecordsRepository inventoryRecordsRepository;

    @Autowired
    InventoryModel inventoryModel;
    
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
       
        
        InventoryModel iModel = inventoryRepository.findById(id).get();
     
        Integer total = inventory.getTotalNo();
      
        Integer issuedNo = iModel.getIssuedNo();
        
                //inventoryModel = inventoryRepository.findById(id).get();
               // if (iModel != null){ 
                    
                  
                    iModel.setAvailable(total  - issuedNo - iModel.getBookedNo()  ) ;
                    iModel.setTotalNo(total) ;
                     
                    
                    
                
       
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

    public Optional<InventoryModel> findByItem(String item) {

        Long id = inventoryModel.getId();
        return inventoryRepository.findById(id);
    }
    public Collection<InventoryModel> listSportItemsForGraph() {
        return inventoryRepository.findAll();
    }
    
   public InventoryModel saveNewItem(String name, String item, Integer total, MultipartFile file){
    InventoryModel iMod = new InventoryModel();
    InventoryModel existing = inventoryRepository.findBySportItem(item);      //
    if (existing != null) {
        System.out.println("this item exists");
    }

   
    String filename = StringUtils.cleanPath(file.getOriginalFilename());
    if (filename.contains("..")){
        System.out.println("not a valid file");
    };
    try {
        iMod.setImage(Base64.getEncoder().encodeToString(file.getBytes()));
    } catch (IOException e) {
        
        e.printStackTrace();
    }
    iMod.setSportItem(item);
    iMod.setSport_name(name);
    iMod.setTotalNo(total);
    iMod.setAvailable(total);
    iMod.setBookedNo(0);
    iMod.setIssuedNo(0);
 return inventoryRepository.save(iMod);
   }
}
