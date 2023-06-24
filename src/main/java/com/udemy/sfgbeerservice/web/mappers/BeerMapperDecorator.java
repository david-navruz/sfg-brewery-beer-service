package com.udemy.sfgbeerservice.web.mappers;

import com.udemy.model.BeerDto;
import com.udemy.sfgbeerservice.domain.Beer;
import com.udemy.sfgbeerservice.services.inventory.BeerInventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;


public abstract class BeerMapperDecorator implements BeerMapper {

    private BeerInventoryService beerInventoryService;
    private BeerMapper mapper;

    @Autowired
    public void setBeerInventoryService(BeerInventoryService beerInventoryService) {
        this.beerInventoryService = beerInventoryService;
    }

    @Autowired
    @Qualifier("delegate")
    public void setMapper(BeerMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public BeerDto beerToBeerDtoWithInventory(Beer beer) {
        BeerDto beerDto = mapper.beerToBeerDto(beer);
        beerDto.setQuantityInStock(beerInventoryService.getInStockInventory(beer.getId()));
        return beerDto;
    }

}
