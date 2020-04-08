package com.aaluni.restful.filtering;

import java.util.Arrays;
import java.util.List;

import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;

@RestController
public class FilteringController {
	
	@GetMapping(path = "/filteredbean")
	public SampleBean filteringMethod() {
		return new SampleBean("value1", "value2", "value3");
	}
	
	@GetMapping(path="/dynamicfilter1")
	public MappingJacksonValue dynamicFilter1() {
		
		DynamicFilteredBean bean = new DynamicFilteredBean("value1", "value2", "value3");
		SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("field1", "field2");
		FilterProvider filterProviders = new SimpleFilterProvider().addFilter("filtername", filter);
		MappingJacksonValue mappedValue = new MappingJacksonValue(bean);
		mappedValue.setFilters(filterProviders);
		
		return mappedValue;
	}
	
	@GetMapping(path="/dynamicfilter2")
	public MappingJacksonValue dynamicFilter2() {
		
		List<DynamicFilteredBean> beans = Arrays.asList(new DynamicFilteredBean("value1", "value2", "value3"), new DynamicFilteredBean("value4", "value4", "value6"));;
		SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("field2", "field3");
		FilterProvider filterProviders = new SimpleFilterProvider().addFilter("filtername", filter);
		MappingJacksonValue mappedValue = new MappingJacksonValue(beans);
		mappedValue.setFilters(filterProviders);
		
		return mappedValue;
	}	
}
