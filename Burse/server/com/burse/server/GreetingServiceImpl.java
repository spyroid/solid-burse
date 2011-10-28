package com.burse.server;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.burse.server.IndexEntry.SearchResult;
import com.burse.shared.GreetingService;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * The server side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class GreetingServiceImpl extends RemoteServiceServlet implements
		GreetingService {

	private static IndexEntry entry;

	static {

		Map<String, Object> map = new HashMap<String, Object>();
		add(map, "Apple MacBook Air 11\" i5 ");
		add(map, "Apple MacBook Air 13\" i5 ");
		add(map, "Apple MacBook Pro 13\" i5");
		add(map, "Apple MacBook Pro 15\" i5");
		add(map, "Apple MacBook Pro 17\" i7");
		add(map, "Hewlett Packard ProBook 15\" i5 4GB");
		add(map, "Lenovo ThinkPad 15\"i7 8GB ");
		add(map, "SONY VAIO 13\" 6GB");
		add(map, "ASUS U35 11\" 8GB");
		entry = IndexEntry.buildIndex(map);
	}

	public String greetServer(String input) throws IllegalArgumentException {
		return "";
	}

	@Override
	public String[] queryProducts(String query) {
		SearchResult search = IndexEntry.search(query, entry);
		Collection<Object> result = search.getResult();
		Object[] array = result.toArray();
		String[] returnValue = new String[array.length];
		System.arraycopy(array, 0, returnValue, 0, array.length);
	
		return returnValue;
	}

	public static void add(Map<String, Object> map, String value) {
		map.put(value, value);
	}

}
