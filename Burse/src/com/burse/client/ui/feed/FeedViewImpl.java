package com.burse.client.ui.feed;

import com.burse.shared.FeedDto;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.SplitLayoutPanel;
import com.google.gwt.user.client.ui.Widget;

public class FeedViewImpl extends Composite {
    interface FeedViewBinder extends UiBinder<Widget, FeedViewImpl> {
    }

    private static FeedViewBinder uiBinder = GWT.create(FeedViewBinder.class);

    @UiField(provided = true) FeedList feedList = new FeedList();
    @UiField(provided = true) SplitLayoutPanel feedSplitPanel = new SplitLayoutPanel(1);
    
    public FeedViewImpl() {
        initWidget(uiBinder.createAndBindUi(this));
        
        feedList.addFeedOnTop(new FeedDto("Продам корову", 1));
        feedList.addFeedOnTop(new FeedDto("Продам корову", 2));
        feedList.addFeedOnTop(new FeedDto("Продам корову", 3));
    }
}
