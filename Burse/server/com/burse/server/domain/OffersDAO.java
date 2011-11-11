package com.burse.server.domain;


import com.googlecode.objectify.Key;
import com.googlecode.objectify.ObjectifyService;

public class OffersDAO extends AbstractDAO<Offer, Long>{

	public OffersDAO() {
		super(Offer.class);
	}

	static {
		ObjectifyService.register(Offer.class);

	}

	@Override
	public Key<Offer> getKey(Offer object) {
		return Key.create(Offer.class, object.id);
	}

	@Override
	public Long getId(Offer object) {
		return object.id;
	}

	@Override
	public void setId(Offer object, Key<Offer> objKey) {
		object.id = objKey.getId();
		
	}

}
