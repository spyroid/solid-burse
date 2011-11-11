package com.burse.server.domain;

import java.util.List;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyService;

public abstract class AbstractDAO<T extends Object, K extends Object> {

	protected Class<T> clazz;

	protected Objectify obf = ObjectifyService.begin();
	
	protected AbstractDAO(Class<T> clazz){
		this.clazz = clazz;
	}
	

	public List<T> list() {
		return obf.query(clazz).list();
	}

	public T save(T object) {
		Key<T> key = getKey(object);
		T returnValue = null;
		if (key != null)
			returnValue = obf.get(key);
		if (returnValue == null) {
			returnValue = object;
			Key<T> put = obf.put(object);
			setId(object, put);
			return returnValue;
		}
		obf.put(object);
		return object;
	}

	public void delete(T t) {
		obf.delete(t);
	}

	public abstract Key<T> getKey(T object);

	public abstract K getId(T object);

	public abstract void setId(T object, Key<T> objKey);

}
