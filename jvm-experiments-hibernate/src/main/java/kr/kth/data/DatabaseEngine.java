package kr.kth.data;

/**
 * Database Engine 종류를 나타냅니다.
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 12. 3.
 */
public enum DatabaseEngine {

	H2("h2"),

	HSqlDb("hsqldb"),

	HSqlDbByFile("hsqhdbbyfile"),

	MySql("mysql"),

	PostgreSql("postgresql"),

	Oracle("oracle");

	private String engineName;

	DatabaseEngine(String engineName) {
		this.engineName = engineName.toLowerCase();
	}

	@Override
	public String toString() {
		return engineName;
	}
}
