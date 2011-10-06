package com.burse.client.ui.feed;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class FeedViewImpl extends Composite {
    interface FeedViewBinder extends UiBinder<Widget, FeedViewImpl> {
    }

    private static FeedViewBinder uiBinder = GWT.create(FeedViewBinder.class);

    public FeedViewImpl() {
        initWidget(uiBinder.createAndBindUi(this));
    }
}
