package model;

import java.util.Map;

import model.*;

public class KMap<T extends KObject, U extends KObject> {

	private Map<T, U> map;
	
	public KMap() {}

	public Map<T, U> getMap() {
		return map;
	}

	public void setMap(Map<T, U> map) {
		this.map = map;
	}
}