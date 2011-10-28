package com.burse.client.ui.catalog;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class ProductBrowserView extends Composite {

	private static ProductBrowserViewUiBinder uiBinder = GWT.create(ProductBrowserViewUiBinder.class);

	interface ProductBrowserViewUiBinder extends UiBinder<Widget, ProductBrowserView> {
	}

	public ProductBrowserView() {
		initWidget(uiBinder.createAndBindUi(this));
	}

}
