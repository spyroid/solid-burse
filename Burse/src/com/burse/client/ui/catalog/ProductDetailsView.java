package com.burse.client.ui.catalog;

import com.burse.shared.ProductDto;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.SpanElement;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class ProductDetailsView extends Composite {

	private static ProductDetailsViewUiBinder uiBinder = GWT.create(ProductDetailsViewUiBinder.class);

	interface ProductDetailsViewUiBinder extends UiBinder<Widget, ProductDetailsView> {
	}

	protected ProductDto product;
	
	@UiField
	protected Label productName;

	@UiField
	protected SpanElement avgPrice;
	@UiField
	protected SpanElement offers;
	@UiField
	protected Image trends;
	@UiField
	protected Image productImage;


	public ProductDetailsView(ProductDto product) {
		initWidget(uiBinder.createAndBindUi(this));
		productName.setText(product.name);
		
		
		avgPrice.setInnerText(convertToCurrencyString(product));
		offers.setInnerText(product.buy + " " + product.sell);
	
		trends.setUrl("http://www.google.com/trends/viz?q="+product.name+"&graph=weekly_img&sa=N");
		productImage.setUrl(product.image);
	}

	private String convertToCurrencyString(ProductDto product) {
		return "~" + product.avgPrice + "$";
	}

}
