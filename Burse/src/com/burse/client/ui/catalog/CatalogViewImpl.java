package com.burse.client.ui.catalog;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class CatalogViewImpl extends Composite {
	interface CatalogViewBinder extends UiBinder<Widget, CatalogViewImpl> {
	}

	private static CatalogViewBinder uiBinder = GWT
			.create(CatalogViewBinder.class);

	public CatalogViewImpl() {
		initWidget(uiBinder.createAndBindUi(this));
	}
}
