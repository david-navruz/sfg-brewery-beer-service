package com.udemy.sfgbeerservice.web.controller;

import com.udemy.model.BeerDto;
import com.udemy.model.BeerPagedList;
import com.udemy.model.BeerStyleEnum;
import com.udemy.sfgbeerservice.services.BeerService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;

import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@RequestMapping("api/v1/beer")
@RestController
public class BeerController {

    private String baseUrl = "/api/v1/beer/";
    private static final Integer DEFAULT_PAGE_NUMBER = 0;
    private static final Integer DEFAULT_PAGE_SIZE = 25;

    private final BeerService beerService;


    @GetMapping(produces = { "application/json" }, path = "beer")
    public ResponseEntity<BeerPagedList> listBeers(@RequestParam(value = "pageNumber", required = false) Integer pageNumber,
                                                   @RequestParam(value = "pageSize", required = false) Integer pageSize,
                                                   @RequestParam(value = "beerName", required = false) String beerName,
                                                   @RequestParam(value = "beerStyle", required = false) BeerStyleEnum beerStyle,
                                                   @RequestParam(value = "showInventoryOnHand", required = false) Boolean showInventoryOnHand) {
        if(showInventoryOnHand == null) {
            showInventoryOnHand = false;
        }
        if(pageNumber == null || pageNumber < 0){
            pageNumber = DEFAULT_PAGE_NUMBER;
        }
        if(pageSize == null || pageSize < 1) {
            pageSize = DEFAULT_PAGE_SIZE;
        }

        BeerPagedList beerList = beerService.listBeers(beerName, beerStyle, PageRequest.of(pageNumber, pageSize), showInventoryOnHand);
        return new ResponseEntity<>(beerList, HttpStatus.OK);

    }


    @GetMapping("/{beerId}")
    public ResponseEntity<BeerDto> getBeerById(@PathVariable("beerId") UUID beerId,
                                               @RequestParam(value = "showInventoryOnHand", required = false) Boolean showInventoryOnHand
                                               ) {
        if(showInventoryOnHand == null) {
            showInventoryOnHand = false;
        }
        return new ResponseEntity<>(beerService.findBeerById(beerId, showInventoryOnHand), HttpStatus.OK);
    }


    @PostMapping(path = "beer")
    public ResponseEntity saveNewBeer(@Valid @RequestBody BeerDto beerDto){
        BeerDto savedDto = beerService.saveNewBeer(beerDto);
        HttpHeaders httpHeaders = new HttpHeaders();
        //todo hostname for uri
        httpHeaders.add("Location", "/api/v1/beer_service/" + savedDto.getId().toString());
        return new ResponseEntity(httpHeaders, HttpStatus.CREATED);
    }


    @PutMapping(path = {"beer/{beerId}"}, produces = { "application/json" })
    public ResponseEntity updateBeer(@PathVariable("beerId") @NotNull UUID beerId, @RequestBody @Validated BeerDto beerDto) {
        beerService.updateBeer(beerId, beerDto);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }


    @GetMapping("beerUpc/{upc}")
    public ResponseEntity<BeerDto> getBeerByUpc(@PathVariable("upc") String upc){
        return new ResponseEntity<>(beerService.findBeerByUpc(upc), HttpStatus.OK);
    }

    @DeleteMapping({"beer/{beerId}"})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBeer(@PathVariable("beerId") UUID beerId){
        beerService.deleteById(beerId);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ResponseEntity<List> badReqeustHandler(ConstraintViolationException e){
        List<String> errors = new ArrayList<>(e.getConstraintViolations().size());

        e.getConstraintViolations().forEach(constraintViolation -> {
            errors.add(constraintViolation.getPropertyPath().toString() + " : " + constraintViolation.getMessage());
        });
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

}
