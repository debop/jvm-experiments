package springbook.chap06;

/**
 * springbook.chap05.Level
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 11. 17.
 */
public enum Level {

	BASIC(1),
	SILVER(2),
	GOLD(3);

	private final int level;

	Level(int value) {
		this.level = value;
	}

	public int intValue() {
		return level;
	}

	public static Level valueOf(int value) {
		switch (value) {
			case 1:
				return BASIC;
			case 2:
				return SILVER;
			case 3:
				return GOLD;
			default:
				throw new IllegalArgumentException("Unknown Level: " + value);
		}
	}
}
