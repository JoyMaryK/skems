package com.mj.skems.inventory;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RequestMapping("/inventory")
public class InventoryController {

    @Autowired
    InventoryRecordsService service;


    @PostMapping("/book")
    public String saveBooking(@ModelAttribute("inventoryRecords") InventoryRecords inventoryRecords){

        service.saveBooking(inventoryRecords);
        return "photo-detail";
    }
}
