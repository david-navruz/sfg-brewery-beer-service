package com.udemy.sfgbeerservice.services.inventory;

import java.util.UUID;

public interface BeerInventoryService {

    Integer getInStockInventory(UUID beerId);

}
