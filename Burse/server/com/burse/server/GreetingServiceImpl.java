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

/**
 * The server side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class GreetingServiceImpl extends RemoteServiceServlet implements
		GreetingService {

	private ProductDAO productsDao = new ProductDAO();
	private OffersDAO offersDao = new OffersDAO();

	@Override
	public String greetServer(String input) throws IllegalArgumentException {
		return "";
	}

	@Override
	public ProductDto[] queryProducts(String query) {
		Collection<Product> list = productsDao.search(query);
		ProductDto[] returnValue = new ProductDto[list.size()];
		int counter = 0;
		for (Product product : list) {
			
			ProductDto productDto = new ProductDto();
			productDto.id = product.id.toString();
			productDto.name = product.name;
			returnValue[counter++] = productDto;
			
		}
	
		return returnValue;
	}

	public static void add(Map<String, Object> map, String value) {
		map.put(value, value);
	}

	@Override
	public ArrayList<FeedDto> listFeeds() {
		List<Offer> listOffers = offersDao.list();
		ArrayList<FeedDto> feeds = new ArrayList<FeedDto>(listOffers.size());
		for (Offer offer : listOffers) {
			Product product = productsDao.getProduct(offer.productOffered);
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
