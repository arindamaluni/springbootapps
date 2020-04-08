package com.aaluni.microservices.currencyexchangeservice;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;



@Entity
public class ExchangeValue {
	
	@Id
	private long id;
	@Column(name = "currency_from")
	private String from;
	@Column(name = "currency_to")
	private String to;
	private BigDecimal conversionFactor;
	//instance identifier for local instances
	private String port;
	
	
	public ExchangeValue(long id, String from, String to, BigDecimal conversionFactor) {
		super();
		this.id = id;
		this.from = from;
		this.to = to;
		this.conversionFactor = conversionFactor;
	}


	public long getId() {
		return id;
	}


	public void setId(long id) {
		this.id = id;
	}


	public String getFrom() {
		return from;
	}


	public void setFrom(String from) {
		this.from = from;
	}


	public String getTo() {
		return to;
	}


	public void setTo(String to) {
		this.to = to;
	}


	public BigDecimal getConversionFactor() {
		return conversionFactor;
	}


	public void setConversionFactor(BigDecimal conversionFactor) {
		this.conversionFactor = conversionFactor;
	}


	public ExchangeValue() {
		// TODO Auto-generated constructor stub
	}


	public String getPort() {
		return port;
	}


	public void setPort(String port) {
		this.port = port;
	}
	
	
	
}
