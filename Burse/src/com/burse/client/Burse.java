package com.burse.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.RootLayoutPanel;

public class Burse implements EntryPoint {
	public void onModuleLoad() {
		final ClientFactory clientFactory = GWT.create(ClientFactory.class);
		RootLayoutPanel.get().add(clientFactory.getApplicationView());
	}
}
