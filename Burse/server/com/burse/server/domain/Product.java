package com.burse.server.domain;

import javax.persistence.Id;

public class Product {
	@Id public Long id;
	public String name;
	public int offersCount;
	public double averagePrice;
	

}
