package com.udemy.msscbeerservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.udemy.msscbeerservice.bootstrap.BeerLoader;
import com.udemy.msscbeerservice.services.BeerService;
import com.udemy.msscbeerservice.web.controller.BeerController;
import com.udemy.model.BeerDto;
import com.udemy.model.BeerStyleEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BeerController.class)
public class BeerControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    BeerService beerService;

    private String baseUrl = "/api/v1/beer/";

    @Test
    void testGetBeerById() throws Exception {
     //   given(beerService.getById(any(), anyBoolean())).willReturn()
    //    mockMvc.perform(get(baseUrl + UUID.randomUUID().toString().accept(MediaType.APPLICATION_JSON)) )
           //     .andExpect(status().isOk());
    }


/*
    BeerDto getValidBeerDto(){
        return BeerDto.builder()
                .beerName("My Beer")
                .beerStyle(BeerStyleEnum.ALE)
                .price(new BigDecimal("2.99"))
                .upc(BeerLoader.BEER_1_UPC)
                .build();
    }*/

}
