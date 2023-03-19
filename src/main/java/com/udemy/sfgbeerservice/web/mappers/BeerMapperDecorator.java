package com.udemy.sfgbeerservice.web.mappers;

import com.udemy.model.BeerDto;
import com.udemy.sfgbeerservice.domain.Beer;
import com.udemy.sfgbeerservice.services.inventory.BeerInventoryService;
import org.springframework.beans.factory.annotation.Autowired;


public abstract class BeerMapperDecorator implements BeerMapper {

    private BeerInventoryService beerInventoryService;
    private BeerMapper mapper;

    @Autowired
    public void setBeerInventoryService(BeerInventoryService beerInventoryService) {
        this.beerInventoryService = beerInventoryService;
    }

    @Autowired
    public void setMapper(BeerMapper mapper) {
        this.mapper = mapper;
    }


    @Override
    public BeerDto beerToBeerDto(Beer beer) {
        return null;
    }

    @Override
    public BeerDto beerToBeerDtoWithInventory(Beer beer) {
        return null;
    }

    @Override
    public Beer beerDtoToBeer(BeerDto dto) {
        return null;
    }
}
