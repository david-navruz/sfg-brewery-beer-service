package com.udemy.sfgbeerservice.services.order;

import com.udemy.model.BeerOrderDto;
import com.udemy.sfgbeerservice.repositories.BeerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicInteger;

@RequiredArgsConstructor
@Slf4j
@Component
public class BeerOrderValidator {

    private BeerRepository beerRepository;

    public Boolean validateOrder(BeerOrderDto beerOrderDto){
        AtomicInteger beersNotFound = new AtomicInteger();
        beerOrderDto.getBeerOrderLines().stream()
                .forEach(beerOrderLineDto -> {
                    if (beerRepository.findByUpc(beerOrderLineDto.getUpc()) == null){
                        beersNotFound.incrementAndGet();
                    }
                });
        //fail order if UPC not found
        return beersNotFound.get() == 0;
    }


}
