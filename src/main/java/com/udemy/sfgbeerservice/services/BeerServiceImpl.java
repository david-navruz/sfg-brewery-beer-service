package com.udemy.sfgbeerservice.services;

import brave.Tracer;
import com.udemy.model.BeerDto;
import com.udemy.model.BeerPagedList;
import com.udemy.model.BeerStyleEnum;
import com.udemy.sfgbeerservice.domain.Beer;
import com.udemy.sfgbeerservice.repositories.BeerRepository;
import com.udemy.sfgbeerservice.web.mappers.BeerMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;


@Slf4j
@RequiredArgsConstructor
@Service
public class BeerServiceImpl implements BeerService {

    @Autowired
    private final BeerRepository beerRepository;

    @Autowired
    private final BeerMapper beerMapper;

    @Autowired
    private final Tracer tracer;

    @Override
    public BeerPagedList listBeers(String beerName, BeerStyleEnum beerStyle, PageRequest pageRequest, Boolean showInventoryOnHand) {
        log.debug("Listing Beers");
        BeerPagedList beerPagedList;
        Page<Beer> beerPage;

        if (!Strings.isEmpty(beerName) && !Strings.isEmpty(beerStyle.toString())) {
            //search both
            beerPage = beerRepository.findAllByBeerNameAndBeerStyle(beerName, beerStyle, pageRequest);
        } else if (!Strings.isEmpty(beerName) && Strings.isEmpty(beerStyle.toString())) {
            //search beer_service name
            beerPage = beerRepository.findAllByBeerName(beerName, pageRequest);
        }
        else if (Strings.isEmpty(beerName) && !Strings.isEmpty(beerStyle.toString())){
            beerPage = beerRepository.findAllByBeerStyle(beerStyle, pageRequest);
        }
        else {
            beerPage = beerRepository.findAll(pageRequest);
        }

        if (showInventoryOnHand) {
            beerPagedList = new BeerPagedList(beerPage
                    .getContent()
                    .stream()
                    .map(beerMapper::beerToBeerDtoWithInventory)
                    .collect(Collectors.toList()),
                    PageRequest
                            .of(beerPage.getPageable().getPageNumber(),
                                    beerPage.getPageable().getPageSize()),
                    beerPage.getTotalElements());
        }
        else {
            beerPagedList = new BeerPagedList(beerPage
                    .getContent()
                    .stream()
                    .map(beerMapper::beerToBeerDto)
                    .collect(Collectors.toList()),
                    PageRequest
                            .of(beerPage.getPageable().getPageNumber(),
                                    beerPage.getPageable().getPageSize()),
                    beerPage.getTotalElements());
        }
        return beerPagedList;
    }


    @Cacheable(cacheNames = "beerCache", key = "#beerId", condition = "#showInventoryOnHand == false ")
    @Override
    public BeerDto findBeerById(UUID beerId, Boolean showInventoryOnHand) {
        log.debug("Finding Beer by id: " + beerId);
        Optional<Beer> beerOptional = beerRepository.findById(beerId);
        if (beerOptional.isPresent()) {
            log.debug("Found BeerId: " + beerId);
            if (showInventoryOnHand) {
                beerMapper.beerToBeerDtoWithInventory(beerOptional.get());
            } else {
                beerMapper.beerToBeerDto(beerOptional.get());
            }
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Not Found. UUID: " + beerId);
        }
        return null;
    }

    @Override
    public BeerDto saveNewBeer(BeerDto beerDto) {
        Beer savedBeer = beerRepository.save(beerMapper.beerDtoToBeer(beerDto));
        return beerMapper.beerToBeerDto(savedBeer);
    }

    @Override
    public void updateBeer(UUID beerId, BeerDto beerDto) {
        Optional<Beer> beerOptional = beerRepository.findById(beerDto.getId());
        beerOptional.ifPresentOrElse(beer -> {
                    beer.setBeerName(beerDto.getBeerName());
                    beer.setBeerStyle(beerDto.getBeerStyle().toString());
                    beer.setId(beerDto.getId());
                },
                () -> {
                    throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Not Found. UUID: " + beerId);
                }
        );
    }

    @Override
    public BeerDto findBeerByUpc(String upc) {
        return beerMapper.beerToBeerDto(beerRepository.findByUpc(upc));
    }

    @Override
    public void deleteById(UUID beerId) {
        beerRepository.deleteById(beerId);
    }
}
