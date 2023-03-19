package com.udemy.sfgbeerservice.services.inventory;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Profile("local-discovery")
@RequiredArgsConstructor
@Service
public class BeerInventoryServiceFeign implements BeerInventoryService {


    @Override
    public Integer getInStockInventory(UUID beerId) {
        return null;
    }
}
