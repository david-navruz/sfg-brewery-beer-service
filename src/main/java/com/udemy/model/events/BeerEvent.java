package com.udemy.model.events;

import com.udemy.model.BeerDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class BeerEvent implements Serializable {

    static final long serialVersionUID = -5781515597148163111L;

    BeerDto beerDto;

}
