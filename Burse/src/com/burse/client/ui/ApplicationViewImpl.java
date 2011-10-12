package com.burse.client.ui;

import com.burse.client.ui.catalog.CatalogView;
import com.burse.client.ui.feed.FeedViewImpl;
import com.burse.client.ui.settings.SettingsViewImpl;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

public class ApplicationViewImpl extends Composite implements ApplicationView {
    interface ApplicationViewBinder extends UiBinder<Widget, ApplicationViewImpl> {
    }

    private static ApplicationViewBinder uiBinder = GWT.create(ApplicationViewBinder.class);

    @UiField SimplePanel mainContent;
    @UiField InlineLabel messages;
    @UiField(provided = true) InlineLabel username = new InlineLabel("user@server.local");
    @UiField Anchor settingsLink;

    public ApplicationViewImpl() {
        initWidget(uiBinder.createAndBindUi(this));
    }
    
    @UiHandler("settingsLink")
    void doSettingsClick(ClickEvent e){
        changeContent(new SettingsViewImpl());
    }

    @UiHandler("feedLink")
    void doFeedClick(ClickEvent e){
        changeContent(new FeedViewImpl());
    }

    @UiHandler("catalogLink")
    void doCatalogClick(ClickEvent e){
        changeContent(new CatalogView());
    }

    private void changeContent(Widget w) {
        mainContent.clear();
        mainContent.add(w);
    }
}
