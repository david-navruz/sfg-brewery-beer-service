package com.udemy.sfgbeerservice.services.brewing;

import com.udemy.model.BeerDto;
import com.udemy.model.events.BrewBeerEvent;
import com.udemy.model.events.NewInventoryEvent;
import com.udemy.sfgbeerservice.config.JmsConfig;
import com.udemy.sfgbeerservice.domain.Beer;
import com.udemy.sfgbeerservice.repositories.BeerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class BrewBeerListener {

    private final BeerRepository beerRepository;
    private final JmsTemplate jmsTemplate;

    @Transactional
    @JmsListener(destination = JmsConfig.BREWING_REQUEST_QUEUE)
    public void listen(BrewBeerEvent brewBeerEvent) {
        BeerDto beerDto = brewBeerEvent.getBeerDto();   // getting the BeerDto from the event
        Beer beer = beerRepository.getOne(beerDto.getId()); // getting the Beer entity from the db
        NewInventoryEvent newInventoryEvent = new NewInventoryEvent(beerDto);   // creating a new inventory event

        log.debug("Brewed beer " + beer.getMinimumInStock() +", QOH: " +beerDto.getQuantityInStock());
        jmsTemplate.convertAndSend(JmsConfig.NEW_INVENTORY_QUEUE, newInventoryEvent); // sending a message to the new inventory jms queue
    }



}

/**
The @Transactional annotation is metadata that specifies that an interface, class, or method must have transactional semantics;
 for example, "start a brand new read-only transaction when this method is invoked, suspending any existing transaction".
 */