package com.udemy.sfgbeerservice.services.order;

import com.udemy.model.events.ValidateBeerOrderRequest;
import com.udemy.model.events.ValidateBeerOrderResult;
import com.udemy.sfgbeerservice.config.JmsConfig;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class BeerOrderValidationListener {

    private BeerOrderValidator validator;
    private JmsTemplate jmsTemplate;

    @JmsListener(destination = JmsConfig.VALIDATE_ORDER_QUEUE)
    public void listen(ValidateBeerOrderRequest event){
        Boolean beerOrderisValid = validator.validateOrder(event.getBeerOrderDto());
        log.debug("Validation Result for Order Id: " + event.getBeerOrderDto() + " is: " + beerOrderisValid);
        jmsTemplate.convertAndSend(JmsConfig.VALIDATE_ORDER_RESPONSE_QUEUE, ValidateBeerOrderResult.builder()
                .orderId(event.getBeerOrderDto().getId())
                .isValid(beerOrderisValid)
                .build());
    }


}
