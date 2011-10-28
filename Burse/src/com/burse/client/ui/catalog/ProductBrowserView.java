package com.burse.client.ui.catalog;

import com.burse.shared.ProductDto;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.DivElement;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.Widget;

public class ProductBrowserView extends Composite {

	private static ProductBrowserViewUiBinder uiBinder = GWT.create(ProductBrowserViewUiBinder.class);

	interface ProductBrowserViewUiBinder extends UiBinder<Widget, ProductBrowserView> {
	}

	@UiField
	protected DivElement manufactures;
	
	@UiField
	protected ScrollPanel browseArea;
	
	
	ProductDto[] products = {
			new ProductDto("1", "MacBookPro 13\"", "1000", "Apple"),
			new ProductDto("2", "MacBookPro 15\"", "1000", "Apple"),
			new ProductDto("3", "MacBookPro 17\"", "1000", "Apple"),
			new ProductDto("4", "MacBook Air 11\"", "1000", "Apple"),
			new ProductDto("5", "MacBook Air 13\"", "1000", "Apple"),
	};
	
	public ProductBrowserView() {
		initWidget(uiBinder.createAndBindUi(this));
		
		
		
		ProductsWidget productsWidget = new ProductsWidget(products);
		browseArea.add(productsWidget);
		
	}

}
