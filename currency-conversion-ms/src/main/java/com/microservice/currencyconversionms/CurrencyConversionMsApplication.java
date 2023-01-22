package com.microservice.currencyconversionms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class CurrencyConversionMsApplication {

	public static void main(String[] args) {
		SpringApplication.run(CurrencyConversionMsApplication.class, args);
	}

}
