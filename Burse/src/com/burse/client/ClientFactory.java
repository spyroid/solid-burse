package com.burse.client;

import com.burse.client.ui.ApplicationView;
import com.burse.shared.CatalogServiceAsync;
import com.google.web.bindery.event.shared.EventBus;

public interface ClientFactory {
	EventBus getEventBus();

	ApplicationView getApplicationView();

	CatalogServiceAsync greetingService();
}
