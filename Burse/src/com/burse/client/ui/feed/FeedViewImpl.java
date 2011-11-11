package com.burse.client.ui.feed;

import java.util.ArrayList;

import com.burse.client.ClientFactory;
import com.burse.client.event.FeedSelectedEvent;
import com.burse.client.event.FeedSelectedEventHandler;
import com.burse.shared.FeedDto;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.SplitLayoutPanel;
import com.google.gwt.user.client.ui.Widget;

public class FeedViewImpl extends Composite {
    interface FeedViewBinder extends UiBinder<Widget, FeedViewImpl> {
    }
    private static FeedViewBinder uiBinder = GWT.create(FeedViewBinder.class);
    @UiField(provided = true) FeedList feedList = new FeedList();
    @UiField(provided = true) SplitLayoutPanel feedSplitPanel = new SplitLayoutPanel(1);
    @UiField(provided = true) FeedInfo feedInfo = new FeedInfo();
    
	private ClientFactory clientFactory = GWT.create(ClientFactory.class);

	public FeedViewImpl() {
        initWidget(uiBinder.createAndBindUi(this));
        clientFactory.greetingService().listFeeds(new AsyncCallback<ArrayList<FeedDto>>() {
			
			@Override
			public void onSuccess(ArrayList<FeedDto> result) {
				for (FeedDto feedDto : result) {
					feedList.addFeedOnTop(feedDto);
				}
				
			}
			
			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				
			}
		});
//        feedList.addFeedOnTop(new FeedDto("Apple iPad 2 64G", 1));
//        FeedDto dto = new FeedDto("Apple iPad 2 32G", 2);
//        dto.setFavorite(true);
//		feedList.addFeedOnTop(dto);
//        feedList.addFeedOnTop(new FeedDto("Apple iPhone 4S 64G", 3));
        
        clientFactory.getEventBus().addHandler(FeedSelectedEvent.TYPE, new FeedSelectedEventHandler() {
            @Override
			public void onEvent(FeedSelectedEvent event) {
				FeedDto dto = event.getDto();
				feedInfo.show(dto);
			}
        });
        
    }
}
