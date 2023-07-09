package com.udemy.sfgbeerservice.controller;

import static org.mockito.Mockito.reset;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.udemy.sfgbeerservice.bootstrap.BeerLoader;
import com.udemy.sfgbeerservice.services.BeerService;
import com.udemy.sfgbeerservice.web.controller.BeerController;
import com.udemy.model.BeerDto;
import com.udemy.model.BeerStyleEnum;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = BeerController.class)
public class BeerControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    BeerService beerService;

    private String baseUrl = "/api/v1/beer/";


    BeerDto beerDto;
    BeerDto validBeer;
    BeerDto validReturnBeer;


    @BeforeEach
    void setUp() {
        validBeer = BeerDto.builder()
                .beerName("Beer1")
                .beerStyle(BeerStyleEnum.PALE_ALE)
                .price(new BigDecimal("12.99"))
                .quantityInStock(4)
                .upc(Long.valueOf(BeerLoader.BEER_1_UPC))
                .build();

        validReturnBeer = BeerDto.builder()
                .id(UUID.randomUUID())
                .version(1)
                .beerName("Beer1")
                .beerStyle(BeerStyleEnum.PALE_ALE)
                .price(new BigDecimal("12.99"))
                .quantityInStock(4)
                .upc(Long.valueOf(BeerLoader.BEER_1_UPC))
                .createdDate(OffsetDateTime.now())
                .lastModifiedDate(OffsetDateTime.now())
                .build();
    }

    @AfterEach
    void tearDown() {
        reset(beerService);
    }

    @Test
    void getBeerById() throws Exception {
        mockMvc.perform(get(baseUrl + UUID.randomUUID().toString())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void saveNewBeer() throws Exception {
        BeerDto beerDto1 = getValidBeerDto();
        String beerDtoToJson = objectMapper.writeValueAsString(beerDto1);

        given(beerService.saveNewBeer(any())).willReturn(getValidBeerDto());

        mockMvc.perform(post("/api/v1/beer/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(beerDtoToJson))
                .andExpect(status().isCreated());
    }

    @Test
    void updateBeerById() throws Exception {
        given(beerService.updateBeer(any(), any())).willReturn(getValidBeerDto());

        BeerDto beerDto1 = getValidBeerDto();
        String beerDtoToJson = objectMapper.writeValueAsString(beerDto1);

        mockMvc.perform(put(baseUrl + UUID.randomUUID().toString())
                .contentType(MediaType.APPLICATION_JSON)
                .content(beerDtoToJson))
                .andExpect(status().isNoContent());
    }


    // helper method
    BeerDto getValidBeerDto() {
        return BeerDto.builder()
                .beerName("Efes")
                .beerStyle(BeerStyleEnum.ALE)
                .price(new BigDecimal("2.99"))
                .upc(123123123123123L)
                .build();
    }

    BeerDto getInvalidBeerDto(){
        return BeerDto.builder().beerName("").upc(null).build();

    }


}
