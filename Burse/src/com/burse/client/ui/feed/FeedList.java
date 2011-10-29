package com.burse.client.ui.feed;

import java.util.HashMap;
import java.util.Map;

import com.burse.client.ClientFactory;
import com.burse.client.event.FeedSelectedEvent;
import com.burse.shared.FeedDto;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class FeedList extends ScrollPanel {

	private VerticalPanel container = new VerticalPanel();
	private Map<Integer, FeedCell> feedMap = new HashMap<Integer, FeedCell>(); 
	private FeedCell selectedCell = null;
	private ClientFactory clientFactory = GWT.create(ClientFactory.class);
	
    public FeedList() {
    	container.setWidth("100%");
    	container.setHeight("100%");
    	add(container);
    	addDomHandler(new ClickHandler() {
    	    @Override
			public void onClick(ClickEvent event) {
				selectItem(event);
			}
		}, ClickEvent.getType());
    }

	protected void selectItem(ClickEvent e) {
		Element targetElement = e.getNativeEvent().getEventTarget().cast();
		FeedCell cell = getClickedFeedCell(targetElement);
		if (cell == null) {
			return;
		}
		if (targetElement.getClassName().contains(FeedCell.getDefaultResources().feedListStyle().starButton())) {
			cell.toggleStar();
		} else {
			if (selectedCell != null) {
				selectedCell.deselect();
			}
			cell.select();
			selectedCell = cell;
			clientFactory.getEventBus().fireEvent(new FeedSelectedEvent(cell.getDto()));
		}
	}

	private FeedCell getClickedFeedCell(Element targetElement) {
		while (!targetElement.getClassName().contains(FeedCell.getDefaultResources().feedListStyle().feedCell())) {
			if (this.getElement() == targetElement) {
				return null;
			}
			targetElement = targetElement.getParentElement();
		}
		String s = targetElement.getAttribute("idx");
		try {
			return feedMap.get(Integer.valueOf(s));
		} catch (Exception e) {
			return null;
		}
	}

	public void addFeedOnTop(FeedDto feedDto) {
		FeedCell cell = new FeedCell(feedDto);
		feedMap.put(feedDto.getId(), cell);
		container.add(cell);
	}
}