package com.burse.client;

import com.burse.client.ui.ApplicationView;
import com.burse.client.ui.ApplicationViewImpl;
import com.google.web.bindery.event.shared.EventBus;
import com.google.web.bindery.event.shared.SimpleEventBus;

public class ClientFactoryImpl implements ClientFactory {
    private static final EventBus eventBus = new SimpleEventBus();
    private static final ApplicationView applicationView = new ApplicationViewImpl();

    public ApplicationView getApplicationView() {
        return applicationView;
    }

    public EventBus getEventBus() {
        return eventBus;
    }
}
