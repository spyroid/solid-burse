package com.burse.server;

import java.util.List;

import com.burse.server.domain.Offer;
import com.burse.server.domain.Product;
import com.google.appengine.api.datastore.Cursor;
import com.google.appengine.api.datastore.QueryResultIterable;
import com.google.appengine.api.datastore.QueryResultIterator;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.ObjectifyService;

public class ProductService extends AbstractDAO<Product, Long>{

	protected OffersService offerService;
	static {
		
		ObjectifyService.register(Product.class);
		
	}

	public ProductService() {
		super(Product.class);
		offerService = new OffersService();
		refreshProductOffersCount(null, 0, 100);
	}
	
	
	public Product getProduct(Key<Product> product) {
		return obf.get(product);
	}
	
	
	

	
	
	
	
	

	public Cursor refreshProductOffersCount(Cursor cursor,int offset, int limit) {
		QueryResultIterable<Product> fetchKeys;
		if (cursor == null) {
			fetchKeys = obf.query(Product.class).order("name").limit(limit).offset(offset).fetch();
		} else {
			fetchKeys = obf.query(Product.class).startCursor(cursor).limit(limit).offset(offset).fetch();
		}
		QueryResultIterator<Product> iterator = fetchKeys.iterator();
		int processed = 0;
		for (Product product : fetchKeys) {
			Key<Product> key = Key.create(Product.class, product.id);
			product.offersCount = obf.query(Offer.class).filter("productOffered", key).count();
			obf.async().put(product);
			processed++;
		}
		if(processed < limit) {
			return null;
		}
		
		Cursor returnCursor = iterator.getCursor();
		return returnCursor;
	}


	@Override
	public Key<Product> getKey(Product object) {
		if(object.id == null) {
			return null;
		}
		return Key.create(Product.class, object.id);
	}


	@Override
	public Long getId(Product object) {
		return object.id;
	}


	@Override
	public void setId(Product object, Key<Product> objKey) {
		object.id = objKey.getId();
	}
	

}
