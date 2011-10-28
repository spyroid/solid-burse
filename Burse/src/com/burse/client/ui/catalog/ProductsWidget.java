package com.burse.client.ui.catalog;

import com.burse.shared.ProductDto;
import com.google.gwt.dom.client.AnchorElement;
import com.google.gwt.dom.client.DivElement;
import com.google.gwt.dom.client.ImageElement;
import com.google.gwt.dom.client.Style.Float;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.Widget;


public class ProductsWidget extends Widget {

	
	public ProductsWidget(ProductDto[] widgets) {
		
		setElement(DOM.createDiv());
		for (ProductDto string : widgets) {
			createProductTile(string);
		}
	}

	protected void createProductTile(ProductDto productId) {
		DivElement prodDiv = DOM.createDiv().cast();
		prodDiv.getStyle().setFloat(Float.LEFT);
		prodDiv.getStyle().setWidth(120, Unit.PX);
		prodDiv.getStyle().setHeight(120, Unit.PX);
		prodDiv.getStyle().setMargin(10, Unit.PX);
		
		AnchorElement name =  DOM.createAnchor().cast();
		name.setHref("#catalog:product:" + productId.id);
		name.setInnerText(productId.manufacturer + " " + productId.name);
		prodDiv.appendChild(name);
		prodDiv.appendChild(DOM.createElement("br"));
		ImageElement img = DOM.createImg().cast();
		img.setSrc("/dimages/" + productId.id + "?height=100");//FIXME
		prodDiv.appendChild(img);
		getElement().appendChild(prodDiv);
		
	}
	
	
	
}
