package com.burse.shared;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * The async counterpart of <code>GreetingService</code>.
 */
public interface GreetingServiceAsync {
	void greetServer(String input, AsyncCallback<String> callback)
			throws IllegalArgumentException;

	void queryProducts(String query, AsyncCallback<ProductDto[]> callback);

	void listFeeds(AsyncCallback<ArrayList<FeedDto>> callback);

	void sendFeed(FeedDto dto, AsyncCallback<Void> callback);
}
