package com.burse.server.domain;


import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.burse.server.IndexEntry;
import com.burse.server.IndexEntry.SearchResult;
import com.google.appengine.api.datastore.Cursor;
import com.google.appengine.api.datastore.QueryResultIterable;
import com.google.appengine.api.datastore.QueryResultIterator;
import com.google.appengine.api.memcache.MemcacheService;
import com.google.appengine.api.memcache.MemcacheServiceFactory;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.ObjectifyService;

public class ProductDAO extends AbstractDAO<Product, Long>{

	protected OffersDAO offerService;
	protected MemcacheService mc;
	public static String INDEX_MC_KEY = "PRODUCT_KEYWORD_SEARCH_INDEX";
	static {
		
		ObjectifyService.register(Product.class);
		
	}

	public ProductDAO() {
		super(Product.class);
		offerService = new OffersDAO();
		refreshProductOffersCount(null, 0, 100);
		mc = MemcacheServiceFactory.getMemcacheService();
		rebuildSearchIndex();
	}
	
	
	public Product getProduct(Key<Product> product) {
		return obf.get(product);
	}
	
	public void rebuildSearchIndex() {
			List<Product> list = list();
			Map<String, Object> keyWordMap = new HashMap<String,Object>();
			for (Product product : list) {
				
				keyWordMap.put(product.name, getKey(product));
			}
			IndexEntry buildIndex = IndexEntry.buildIndex(keyWordMap);
			mc.put(INDEX_MC_KEY, buildIndex);
	}
	
	
	@SuppressWarnings("unchecked")
	public Collection<Product> search(String query) {
		IndexEntry indexEntry = (IndexEntry) mc.get(INDEX_MC_KEY);
		SearchResult search = IndexEntry.search(query, indexEntry);
		Collection<Object> result = search.getResult();
		List<Key<Product>> list = new ArrayList<Key<Product>>();
		for (Object object : result) {
			list.add((Key<Product>) object);
		}
		
		
		Map<Key<Product>, Product> map = obf.get(list);
		return map.values();
		
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
