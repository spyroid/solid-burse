package com.burse.client.ui.catalog;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Widget;

public class ParameterSearch extends Composite   {

	private static ParameterSearchUiBinder uiBinder = GWT
			.create(ParameterSearchUiBinder.class);

	interface ParameterSearchUiBinder extends UiBinder<Widget, ParameterSearch> {
	}

	public ParameterSearch() {
		initWidget(uiBinder.createAndBindUi(this));
	}



	public ParameterSearch(String firstName) {
		initWidget(uiBinder.createAndBindUi(this));
	}





}
