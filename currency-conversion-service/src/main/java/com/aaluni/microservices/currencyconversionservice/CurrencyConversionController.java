package com.aaluni.microservices.currencyconversionservice;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class CurrencyConversionController {
	//@Autowired
	//Environment env;
	Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	CurrencyExchangeServiceProxy proxy;
		
	@GetMapping(path="/currency-converter/from/{from}/to/{to}/amount/{amount}")
	public CurrencyConversionBean convertCurrency(@PathVariable String from, @PathVariable String to, @PathVariable BigDecimal amount) {
		
		Map<String, String> uriVariables = new HashMap<String, String>();
		uriVariables.put("from", from);
		uriVariables.put("to", to);
		
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<CurrencyConversionBean> responseEntity = restTemplate.getForEntity("http://localhost:8001/currency-exchange-service/convert/{from}/to/{to}", CurrencyConversionBean.class, uriVariables);
		
		CurrencyConversionBean conversionBean = responseEntity.getBody();
		//String port = env.getProperty("local.server.port");
		//conversionBean.setPort(port);
		conversionBean.setAmount(amount);
		conversionBean.setCalculatedAmount(amount.multiply(conversionBean.getConversionFactor()));
		return  conversionBean;
	}

	@GetMapping(path="/currency-converter-feign/from/{from}/to/{to}/amount/{amount}")
	public CurrencyConversionBean convertCurrencyFeign(@PathVariable String from, @PathVariable String to, @PathVariable BigDecimal amount) {
		
		CurrencyConversionBean conversionBean = proxy.retrieveExchangeValue(from, to);
		//String port = env.getProperty("local.server.port");
		//conversionBean.setPort(port);
		conversionBean.setAmount(amount);
		conversionBean.setCalculatedAmount(amount.multiply(conversionBean.getConversionFactor()));
		logger.info("{}", logger);
		return  conversionBean;
	}

}
