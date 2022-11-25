package com.mj.skems.Inventory;

import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

@Repository
public interface InventoryRepository extends JpaRepository<InventoryModel, Long>{
    
    
    public InventoryModel findBySportItem( String item);

    @Query("SELECT totalNo from InventoryModel r where r.id =:id ")
    public Integer findOldTotalNo(@Param(value="id" )Long id);

   @Query( value = "select distinct i.id from inventory i inner join inventory_records ir where i.sport_item=:item" ,nativeQuery = true)
public Long findIdOfItem(@Param( value= "item") String item);
}
