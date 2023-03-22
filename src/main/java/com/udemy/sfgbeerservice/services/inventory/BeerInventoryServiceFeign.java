package com.udemy.sfgbeerservice.services.inventory;

import com.udemy.model.BeerInventoryDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Slf4j
@Profile({"local-discovery", "digitalocean"})
@RequiredArgsConstructor
@Service
public class BeerInventoryServiceFeign implements BeerInventoryService {

    private InventoryServiceFeignClient inventoryServiceFeignClient;

    @Override
    public Integer getInStockInventory(UUID beerId) {
        log.debug("Calling Inventory Service w/Feign - BeerId: " + beerId);
        Integer inStock = 0;

        try {
            ResponseEntity<List<BeerInventoryDto>> responseEntity = inventoryServiceFeignClient.getOnhandInventory(beerId);

            if (responseEntity.getBody() != null && responseEntity.getBody().size() > 0) {
                log.debug("Inventory found, summing inventory");
                inStock = Objects.requireNonNull(responseEntity.getBody())
                        .stream()
                        .mapToInt(BeerInventoryDto::getQuantityOnHand)
                        .sum();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
