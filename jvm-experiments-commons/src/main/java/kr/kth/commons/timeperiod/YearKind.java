package kr.kth.commons.timeperiod;

/**
 * 년도 종류 (년의 종류에 따라 시작일이 달라집니다) (예: 회계년도의 시작일은 2월이고, 한국의 교육년 시작월은 3월입니다)
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 12. 19.
 */
public enum YearKind {
	/**
	 * 설치된 OS의 문화권에 해당하는 Year
	 */
	SystemYear("System"),
	/**
	 * 현 ThreadContext에 지정된 {@link java.util.Locale}의 Calendar의 Year
	 */
	CalendarYear("Calendar"),
	/**
	 * 회계 년 (2월 시작)
	 */
	FiscalYear("Fiscal"),
	/**
	 * 교육 년 (3월 시작, 9월 시작)
	 */
	SchoolYear("School"),
	/**
	 * 사용자 정의 Year
	 */
	CustomYear("Custom");


	private final String year;

	YearKind(String year) {
		this.year = year;
	}
}
