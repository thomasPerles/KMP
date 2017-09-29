package model;

import java.util.ArrayList;

import kPackageUtils.DataBase;

public class KObject {

	protected String id;
	private DataBase db;
	protected Integer state;
	private ArrayList<KObjectObserver> observers;

	public KObject(String id) {
		this.id = id;
	}
	
	public String getId() {
		return this.id;
	}

	public DataBase getDb() {
		return db;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public void getObservers(KObjectObserver observer) {
		this.observers.add(observer);
	}

	public void notifyAllObservers() {
		for(KObjectObserver observer : observers) {
			observer.update();
		}
	}
}