package com.burse.server.domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Id;

import com.burse.server.IndexEntry;
import com.burse.server.IndexEntry.SearchResult;
import com.google.appengine.api.datastore.Cursor;
import com.google.appengine.api.datastore.QueryResultIterable;
import com.google.appengine.api.datastore.QueryResultIterator;
import com.google.appengine.api.memcache.AsyncMemcacheService;
import com.google.appengine.api.memcache.Expiration;
import com.google.appengine.api.memcache.MemcacheService;
import com.google.appengine.api.memcache.MemcacheService.SetPolicy;
import com.google.appengine.api.memcache.MemcacheServiceFactory;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.Query;
import com.googlecode.objectify.annotation.Serialized;

public class ProductDAO extends AbstractDAO<Product, Long> {

	protected OffersDAO offerService;
	protected MemcacheService mc;
	protected AsyncMemcacheService amc;
	public static String INDEX_MC_KEY = "PRODUCT_KEYWORD_SEARCH_INDEX";
	static {

		ObjectifyService.register(Product.class);
		ObjectifyService.register(ProductIndex.class);

	}

	public ProductDAO() {
		super(Product.class);
		offerService = new OffersDAO();
		refreshProductOffersCount(null, 0, 100);
		mc = MemcacheServiceFactory.getMemcacheService();
		amc = MemcacheServiceFactory.getAsyncMemcacheService();
		rebuildSearchIndex();
	}

	public Product getProduct(Key<Product> product) {
		return obf.get(product);
	}

	public void rebuildSearchIndex() {
		List<Product> list = list();
		Map<String, Object> keyWordMap = new HashMap<String, Object>();
		for (Product product : list) {

			keyWordMap.put(product.name, getKey(product));
		}
		IndexEntry buildIndex = IndexEntry.buildIndex(keyWordMap);
		amc.put(INDEX_MC_KEY, buildIndex, Expiration.byDeltaMillis(60 * 60), SetPolicy.ADD_ONLY_IF_NOT_PRESENT);

		ProductIndex index = new ProductIndex();
		index.id = INDEX_MC_KEY;
		index.index = buildIndex;
		obf.put(index);
	}

	@SuppressWarnings("unchecked")
	public Collection<Product> search(String query) {
		IndexEntry indexEntry = (IndexEntry) mc.get(INDEX_MC_KEY);
		if (indexEntry == null) {
			ProductIndex productIndex = obf.get(ProductIndex.class, INDEX_MC_KEY);
			amc.put(INDEX_MC_KEY, productIndex.index, Expiration.byDeltaSeconds(60 * 60), SetPolicy.ADD_ONLY_IF_NOT_PRESENT);
			indexEntry = productIndex.index;
		}
		SearchResult search = IndexEntry.search(query, indexEntry);
		Collection<Object> result = search.getResult();
		List<Key<Product>> list = new ArrayList<Key<Product>>();
		for (Object object : result) {
			list.add((Key<Product>) object);
		}

		Map<Key<Product>, Product> map = obf.get(list);

		return map.values();

	}

	public Cursor refreshProductOffersCount(Cursor cursor, int offset, int limit) {
		QueryResultIterable<Product> fetchKeys;
		Query<Product> query = obf.query(Product.class).limit(limit).offset(offset);

		if (cursor == null) {
			fetchKeys = query.order("name").fetch();
		} else {
			fetchKeys = query.startCursor(cursor).fetch();
		}
		QueryResultIterator<Product> iterator = fetchKeys.iterator();
		int processed = 0;
		for (Product product : fetchKeys) {
			Key<Product> key = Key.create(Product.class, product.id);
			product.offersCount = obf.query(Offer.class).filter("productOffered", key).count();
			obf.async().put(product);
			processed++;
		}
		if (processed < limit) {
			return null;
		}

		Cursor returnCursor = iterator.getCursor();
		return returnCursor;
	}

	@Override
	public Key<Product> getKey(Product object) {
		if (object.id == null) {
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

	public static class ProductIndex {
		@Id
		String id;
		@Serialized
		IndexEntry index;
	}

}
