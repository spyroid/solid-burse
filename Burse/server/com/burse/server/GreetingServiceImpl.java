package com.burse.server;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.burse.server.IndexEntry.SearchResult;
import com.burse.server.domain.Offer;
import com.burse.server.domain.OffersDAO;
import com.burse.server.domain.Product;
import com.burse.server.domain.ProductDAO;
import com.burse.shared.FeedDto;
import com.burse.shared.GreetingService;
import com.burse.shared.ProductDto;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.googlecode.objectify.Key;

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
		
		
		
		
		ProductDAO service = new ProductDAO();
		OffersDAO offersService = new OffersDAO();
		Random random = new Random();
		List<Product> list = service.list();
		for(Product product:list) {
			Offer offer = new Offer();
			offer.dateAdded = new Date();
			offer.productOffered = Key.create(Product.class,product.id);
			offer.unitPrice = random.nextInt(400) + 500;
			offer.quantity = 500;
			offersService.save(offer);
			add(map,product.name);
		}
		entry = IndexEntry.buildIndex(map);
	}

	@Override
	public String greetServer(String input) throws IllegalArgumentException {
		return "";
	}

	@Override
	public ProductDto[] queryProducts(String query) {
		SearchResult search = IndexEntry.search(query, entry);
		Collection<Object> result = search.getResult();
		if(result == null) {
			return new ProductDto[0];
		}
		Object[] array = result.toArray();
		ProductDto[] returnValue = new ProductDto[array.length];
		System.arraycopy(array, 0, returnValue, 0, array.length);
	
		return returnValue;
	}

	public static void add(Map<String, Object> map, String value) {
		map.put(value, value);
	}

	@Override
	public ArrayList<FeedDto> listFeeds() {
		ProductDAO productService = new ProductDAO();
		OffersDAO offersService = new OffersDAO();
		List<Offer> listOffers = offersService.list();
		ArrayList<FeedDto> feeds = new ArrayList<FeedDto>(listOffers.size());
		for (Offer offer : listOffers) {
			Product product = productService.getProduct(offer.productOffered);
			
			FeedDto feedDto = new FeedDto(product.name, offer.id.intValue());
			feeds.add(feedDto);
			
		}
		
		
		return feeds;
	}

	@Override
	public void sendFeed(FeedDto dto) {
		// TODO Auto-generated method stub
		
	}

}
