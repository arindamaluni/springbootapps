package com.aaluni.microservices.currencyconversionservice;

import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

//Feign without zuul proxy
//@FeignClient(name="currency-exchange-service")
//Feign to zuul proxy
@FeignClient(name="netflix-zuul-api-gateway-server")
//Ribbon is not required when using api gateway. Zuul distributes the load
//@RibbonClient("currency-exchange-service")
public interface CurrencyExchangeServiceProxy {
	////Feign without zuul proxy
	//@GetMapping(path="/currency-exchange-service/convert/{from}/to/{to}")
	//Feign to zuul proxy. Path modified the include the application name that the api proxy will use for discovery
	@GetMapping(path="/currency-exchange-service/currency-exchange-service/convert/{from}/to/{to}")
	public CurrencyConversionBean retrieveExchangeValue(@PathVariable(name = "from") String from, @PathVariable(name = "to") String to);
}
