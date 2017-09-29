package kPackageUtils;

import java.util.ArrayList;

import kPackage.KObject;

public class DataBase {
	
	private ArrayList<KObject> kObjects = new ArrayList<KObject>();
	
	public DataBase() {}

	public ArrayList<KObject> getkObjects() {
		return kObjects;
	}
	
	public void saveData() {}
	
	public void loadDataBase() {}
	
	public void addKObjectToDataBase(KObject object) {
		this.kObjects.add(object);
	}
	
	public KObject findKObjectByID(String id) {
		for (int i = 0; i < this.kObjects.size(); i++) {
			if (this.kObjects.get(i).getId() == id)
				return this.kObjects.get(i);
		}
		return null;
	}
}