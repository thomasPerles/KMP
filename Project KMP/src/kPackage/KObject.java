package kPackage;

import kPackageUtils.*;

public class KObject {

	protected String id;
	private DataBase db;

	public KObject(String id) {
		this.id = id;
	}
	
	public String getId() {
		return this.id;
	}

	public DataBase getDb() {
		return db;
	}
}