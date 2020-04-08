package com.aaluni.microservices.currencyexchangeservice;

import java.math.BigDecimal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CurrencyExchangeController {
	@Autowired
	private Environment env;
	
	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private ExchangeValueRepository repository;
	
	@GetMapping(path="/currency-exchange-service/convert/{from}/to/{to}")
	public ExchangeValue retrieveExchangeValue(@PathVariable String from, @PathVariable String to) {
		ExchangeValue ev = repository.findByFromAndTo(from, to);//new ExchangeValue(1000L, from, to, BigDecimal.valueOf(65L));
		ev.setPort(env.getProperty("local.server.port"));
		logger.info("{}", ev);
		return ev;
	}
}
