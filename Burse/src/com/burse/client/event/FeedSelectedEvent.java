package com.burse.client.event;

import com.burse.shared.FeedDto;
import com.google.gwt.event.shared.GwtEvent;

public class FeedSelectedEvent extends GwtEvent<FeedSelectedEventHandler> {

    public static Type<FeedSelectedEventHandler> TYPE = new Type<FeedSelectedEventHandler>();
	private FeedDto dto;

    public FeedSelectedEvent(FeedDto _dto) {
		dto = _dto;
	}

	@Override
    public Type<FeedSelectedEventHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(FeedSelectedEventHandler handler) {
        handler.onEvent(this);
    }

	public FeedDto getDto() {
		return dto;
	}

	public void setDto(FeedDto dto) {
		this.dto = dto;
	}
}
