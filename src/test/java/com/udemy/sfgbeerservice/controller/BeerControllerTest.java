package com.udemy.sfgbeerservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.udemy.sfgbeerservice.services.BeerService;
import com.udemy.sfgbeerservice.web.controller.BeerController;
import com.udemy.model.BeerDto;
import com.udemy.model.BeerStyleEnum;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@WebMvcTest(BeerController.class)
public class BeerControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    BeerService beerService;

    private String baseUrl = "/api/v1/beer/";

    BeerDto beerDto;
    @BeforeEach
    void setup() {
        beerDto  = BeerDto.builder()
                .beerStyle(BeerStyleEnum.ALE)
                .beerName("notNull")
                .price(new BigDecimal("1.23"))
                .upc(13241214L)
                .quantityInStock(3)
                .build();
    }

    @Test
    void testGetBeerById() throws Exception {
        mockMvc.perform(get(baseUrl + UUID.randomUUID().toString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
/*
    @Test
    void saveNewBeer() throws Exception {
        String beerDtoJson = objectMapper.writeValueAsString(beerDto);
        mockMvc.perform(post(baseUrl)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(beerDtoJson))
                .andExpect(status().isCreated());
    }*/







}
