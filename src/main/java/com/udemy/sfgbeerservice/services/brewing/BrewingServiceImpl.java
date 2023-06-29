package com.udemy.sfgbeerservice.services.brewing;

import com.udemy.model.events.BrewBeerEvent;
import com.udemy.sfgbeerservice.config.JmsConfig;
import com.udemy.sfgbeerservice.domain.Beer;
import com.udemy.sfgbeerservice.repositories.BeerRepository;
import com.udemy.sfgbeerservice.services.inventory.BeerInventoryService;
import com.udemy.sfgbeerservice.web.mappers.BeerMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Slf4j
@Service
public class BrewingServiceImpl implements BrewingService {

    private final BeerRepository beerRepository;
    private final BeerInventoryService beerInventoryService;
    private final JmsTemplate jmsTemplate;
    private final BeerMapper beerMapper;

    @Scheduled(fixedRate = 5000) // checking every 5 seconds
    public void checkForLowInventory() {
        Pageable pageable = PageRequest.of(10, 10);
        Page<Beer> beersPaged = beerRepository.findAll(pageable);
        List<Beer> beers = beersPaged.getContent();
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
