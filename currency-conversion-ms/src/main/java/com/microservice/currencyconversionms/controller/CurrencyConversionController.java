package com.microservice.currencyconversionms.controller;

import com.microservice.currencyconversionms.bean.CurrencyConversion;
import com.microservice.currencyconversionms.proxy.CurrencyExchangeProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.HashMap;

@RestController
public class CurrencyConversionController {

    @Autowired
    private CurrencyExchangeProxy currencyExchangeProxy;

    @GetMapping("/currency-conversion/from/{from}/to/{to}/quantity/{quantity}")
    public CurrencyConversion currencyConversion(@PathVariable String from,
                                                 @PathVariable String to,
                                                 @PathVariable BigDecimal quantity){

        HashMap<String, String> uriVariable = new HashMap<>();
        uriVariable.put("from",from);
        uriVariable.put("to", to);

        ResponseEntity<CurrencyConversion> responseEntity = new RestTemplate()
                .getForEntity("http://localhost:8000/currency-exchange/from/{from}/to/{to}",
                        CurrencyConversion.class, uriVariable);

        CurrencyConversion body = responseEntity.getBody();

        return new CurrencyConversion(body.getId(),
                from,
                to,
                body.getConversionMultiple(),
                quantity,
                quantity.multiply(body.getConversionMultiple()),
                body.getEnvironment()+" Rest template");
    }

    @GetMapping("/currency-conversion-feign/from/{from}/to/{to}/quantity/{quantity}")
    public CurrencyConversion currencyConversionFeign(@PathVariable String from,
                                                 @PathVariable String to,
                                                 @PathVariable BigDecimal quantity){


        CurrencyConversion body = currencyExchangeProxy.currencyExchange(from, to);

        return new CurrencyConversion(body.getId(),
                from,
                to,
                body.getConversionMultiple(),
                quantity,
                quantity.multiply(body.getConversionMultiple()),
                body.getEnvironment()+" feign");
    }
}
