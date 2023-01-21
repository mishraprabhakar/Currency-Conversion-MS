package com.microservices.currencyexchangems.controller;

import com.microservices.currencyexchangems.bean.CurrencyExchange;
import com.microservices.currencyexchangems.repo.CurrencyExchangeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
public class CurrencyExchangeController {
    @Autowired
    private Environment environment;
    @Autowired
    private CurrencyExchangeRepository repository;

    @GetMapping("currency-exchange/from/{from}/to/{to}")
    public CurrencyExchange currencyExchange(@PathVariable String from,
                                             @PathVariable String to) {

        CurrencyExchange currencyExchange = repository.findByFromAndTo(from, to);

        if (currencyExchange == null){
            throw new RuntimeException("No such Data found for "+from+" To "+to);
        }
        String port = environment.getProperty("local.server.port");
        currencyExchange.setPort(port);

        return currencyExchange;
    }
}
