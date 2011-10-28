package com.burse.shared;

import com.google.gwt.user.client.rpc.IsSerializable;

public class ProductDto implements IsSerializable {
	public String id;
	public String name;
	public String image;
	public String avgPrice;
	public int buy;
	public int sell;
}
