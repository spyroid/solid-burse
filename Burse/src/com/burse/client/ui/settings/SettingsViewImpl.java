package com.burse.client.ui.settings;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class SettingsViewImpl extends Composite {
    interface SettingsViewBinder extends UiBinder<Widget, SettingsViewImpl> {
    }

    private static SettingsViewBinder uiBinder = GWT.create(SettingsViewBinder.class);

    public SettingsViewImpl() {
        initWidget(uiBinder.createAndBindUi(this));
    }
}
