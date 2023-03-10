package com.udemy.msscbeerservice.services.brewing;

import com.udemy.model.events.BrewBeerEvent;
import com.udemy.msscbeerservice.config.JmsConfig;
import com.udemy.msscbeerservice.domain.Beer;
import com.udemy.msscbeerservice.repositories.BeerRepository;
import com.udemy.msscbeerservice.services.inventory.BeerInventoryService;
import com.udemy.msscbeerservice.web.mappers.BeerMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Slf4j
@Service
public class BrewingService {

    private final BeerRepository beerRepository;
    private final BeerInventoryService beerInventoryService;
    private final JmsTemplate jmsTemplate;
    private final BeerMapper beerMapper;

    @Scheduled(fixedRate = 5000) // checking every 5 seconds
    public void checkForLowInventory() {
        List<Beer> beers = beerRepository.findAll();
        beers.forEach(beer -> {
            Integer inventoryInStock = beerInventoryService.getInStockInventory(beer.getId());
            log.debug("Min InStock is: " + beer.getMinimumInStock());
            log.debug("Inventory is: " + inventoryInStock);
            // if the number of beers in stock is less or equal to the minimum amount set
            if(inventoryInStock <= beer.getMinimumInStock()){
                jmsTemplate.convertAndSend(JmsConfig.BREWING_REQUEST_QUEUE,
                        new BrewBeerEvent(beerMapper.beerToBeerDto(beer)));
            }
        });
    }

}
