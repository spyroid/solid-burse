package com.burse.client.ui.catalog;

import com.burse.client.css.Resources;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.DivElement;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class CatalogView extends Composite {
	interface CatalogViewBinder extends UiBinder<Widget, CatalogView> {
	}

	private static CatalogViewBinder uiBinder = GWT
			.create(CatalogViewBinder.class);

	@UiField
	protected TextBox searchField;

	@UiField
	protected DivElement displayDiv;

	public CatalogView() {
		initWidget(uiBinder.createAndBindUi(this));
		EmptyTextBoxMaskProvider.register("Type to Search", searchField);

		showDefaultPanel();
	}

	@UiHandler("searchField")
	protected void doSearch(KeyUpEvent event) {
		if (searchField.getText().isEmpty() == false) {
			tryStartSearch();
		} else {
			showDefaultPanel();
		}
	}

	protected void showDefaultPanel() {
		String text = Resources.bundle.catalogPlaceHolder().getText();
		displayDiv.setInnerHTML(text);

	}

	protected void tryStartSearch() {
		displayDiv.setInnerText("Searching");
	}

}
