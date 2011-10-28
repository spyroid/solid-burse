package com.burse.shared;

import com.google.gwt.user.client.rpc.IsSerializable;

public class ProductDto implements IsSerializable {
	public String id;
	public String name;
	public String image;
	public String avgPrice;
	public String manufacturer;
	public int buy;
	public int sell;

	public ProductDto(){
		
	}
	
	
	public ProductDto(String id, String name, String avgPrice, String manufacturer) {
		super();
		this.id = id;
		this.name = name;
		this.avgPrice = avgPrice;
		this.manufacturer = manufacturer;
	}

}
