package kr.kth.commons.timeperiod;

/**
 * kr.kth.commons.timeperiod.HalfYearKind
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 12. 19.
 */
public enum HalfYearKind {

	First(1),
	Second(2);

	private final int halfYear;

	HalfYearKind(int halfYear) {
		this.halfYear = halfYear;
	}

	public final int getValue() {
		return halfYear;
	}
}
