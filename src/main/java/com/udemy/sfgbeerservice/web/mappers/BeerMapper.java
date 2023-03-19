package com.udemy.sfgbeerservice.web.mappers;

import com.udemy.model.BeerDto;
import com.udemy.sfgbeerservice.domain.Beer;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;

@Mapper(uses = {DateMapper.class})
@DecoratedWith(BeerMapperDecorator.class)
public interface BeerMapper {

    BeerDto beerToBeerDto(Beer beer);

    BeerDto beerToBeerDtoWithInventory(Beer beer);

    Beer beerDtoToBeer(BeerDto dto);

}

/**
 * MapStruct, which is, simply put, a Java Bean mapper.
 * Dto to Entity, Entity to Dto Mapper
 */