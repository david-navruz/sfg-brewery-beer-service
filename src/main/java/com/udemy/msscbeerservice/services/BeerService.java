package com.udemy.msscbeerservice.services;

import com.udemy.model.BeerDto;
import com.udemy.model.BeerPagedList;
import com.udemy.model.BeerStyleEnum;
import org.springframework.data.domain.PageRequest;
import java.util.UUID;

public interface BeerService {

    BeerPagedList listBeers(String beerName, BeerStyleEnum beerStyle, PageRequest pageRequest, Boolean showInventoryOnHand);

    BeerDto getById(UUID beerId, Boolean showInventoryOnHand);

    BeerDto saveNewBeer(BeerDto beerDto);

    BeerDto updateBeer(UUID beerId, BeerDto beerDto);

    BeerDto getByUpc(String upc);

}
