package com.burse.client.ui.feed;

import com.burse.client.ClientFactory;
import com.burse.client.event.FeedSelectedEvent;
import com.burse.client.event.FeedSelectedEventHandler;
import com.burse.shared.FeedDto;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.SplitLayoutPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class FeedViewImpl extends Composite {
    interface FeedViewBinder extends UiBinder<Widget, FeedViewImpl> {
    }
    private static FeedViewBinder uiBinder = GWT.create(FeedViewBinder.class);
    @UiField(provided = true) FeedList feedList = new FeedList();
    @UiField(provided = true) SplitLayoutPanel feedSplitPanel = new SplitLayoutPanel(1);
    @UiField(provided = true) VerticalPanel feedInfo = new VerticalPanel();
    
	private ClientFactory clientFactory = GWT.create(ClientFactory.class);

	public FeedViewImpl() {
        initWidget(uiBinder.createAndBindUi(this));
        
        feedList.addFeedOnTop(new FeedDto("Apple iPad 2 64G", 1));
        FeedDto dto = new FeedDto("Apple iPad 2 32G", 2);
        dto.setFavorite(true);
		feedList.addFeedOnTop(dto);
        feedList.addFeedOnTop(new FeedDto("Apple iPhone 4S 64G", 3));
        
        clientFactory.getEventBus().addHandler(FeedSelectedEvent.TYPE, new FeedSelectedEventHandler() {
			@Override
			public void onEvent(FeedSelectedEvent event) {
				FeedDto dto = event.getDto();
				feedInfo.clear();
				feedInfo.add(new HTML(dto.getTitle()));
			}
        });
        
    }
}
