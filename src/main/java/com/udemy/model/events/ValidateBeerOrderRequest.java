package com.udemy.model.events;

import com.udemy.model.BeerOrderDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ValidateBeerOrderRequest {

    private BeerOrderDto beerOrderDto;


}
