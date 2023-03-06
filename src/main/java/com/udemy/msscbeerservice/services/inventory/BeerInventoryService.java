package com.udemy.msscbeerservice.services.inventory;

import java.util.UUID;

public interface BeerInventoryService {

    Integer getInStockInventory(UUID beerId);

}
