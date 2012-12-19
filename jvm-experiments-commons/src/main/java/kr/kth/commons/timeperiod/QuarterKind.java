package kr.kth.commons.timeperiod;

/**
 * 분기 (Quarter) 를 표현합니다.
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 12. 19.
 */
public enum QuarterKind {

	/**
	 * 1분기
	 */
	First(1),
	/**
	 * 2분기
	 */
	Second(2),
	/**
	 * 3분기
	 */
	Third(3),
	/**
	 * 4분기
	 */
	Fouth(4);


	private final int quarter;

	QuarterKind(int quarter) {
		this.quarter = quarter;
	}
}
