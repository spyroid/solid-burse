package com.burse.client;

import com.burse.client.ui.ApplicationView;
import com.burse.client.ui.ApplicationViewImpl;
import com.burse.shared.CatalogService;
import com.burse.shared.CatalogServiceAsync;
import com.google.gwt.core.client.GWT;
import com.google.web.bindery.event.shared.EventBus;
import com.google.web.bindery.event.shared.SimpleEventBus;

public class ClientFactoryImpl implements ClientFactory {
	private static final EventBus eventBus = new SimpleEventBus();
	private static final ApplicationView applicationView = new ApplicationViewImpl();
	private static final CatalogServiceAsync service = GWT.create(CatalogService.class);

	@Override
	public ApplicationView getApplicationView() {
		return applicationView;
	}

	@Override
	public EventBus getEventBus() {
		return eventBus;
	}

	@Override
	public CatalogServiceAsync greetingService() {
		return service;
	}

}
