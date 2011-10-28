package com.burse.client.ui.catalog;

import com.burse.shared.ProductDto;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.AnchorElement;
import com.google.gwt.dom.client.SpanElement;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Widget;

public class ProductDetailsView extends Composite {

	private static ProductDetailsViewUiBinder uiBinder = GWT.create(ProductDetailsViewUiBinder.class);

	interface ProductDetailsViewUiBinder extends UiBinder<Widget, ProductDetailsView> {
	}

	protected ProductDto product;

	@UiField
	AnchorElement manufacturer;
	@UiField
	protected SpanElement productName;
	@UiField
	protected SpanElement avgPrice;
	@UiField
	protected AnchorElement offersSell;
	@UiField
	protected AnchorElement offersBuy;
	@UiField
	protected Image trends;
	@UiField
	protected Image productImage;

	public ProductDetailsView(ProductDto product) {
		initWidget(uiBinder.createAndBindUi(this));
		manufacturer.setHref("#catalog:browse:" + product.manufacturer);
		manufacturer.setInnerText(product.manufacturer);
		productName.setInnerText(product.name);

		avgPrice.setInnerText(convertToCurrencyString(product));
		offersSell.setInnerText("~" + product.sell);
		offersSell.setHref("#feed:sell:" + product.id);
		offersBuy.setInnerText("~" + product.buy);
		offersBuy.setHref("#feed:buy:" + product.id);
		trends.setUrl("http://www.google.com/trends/viz?q=" + product.name + "&graph=weekly_img&sa=N");
		productImage.setUrl(product.image);
	}

	private String convertToCurrencyString(ProductDto product) {
		return "~" + product.avgPrice + "$";
	}

}
