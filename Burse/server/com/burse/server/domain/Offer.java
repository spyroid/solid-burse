package com.burse.server.domain;

import java.util.Date;

import javax.persistence.Id;

import com.googlecode.objectify.Key;

public class Offer {

	@Id public Long id;
	
	public Key<Product> productOffered;
	public Key<CompanyAccount> provider;
	public int quantity;
	public double unitPrice;
	public Date dateAdded;
	public String uuid;
	
}
