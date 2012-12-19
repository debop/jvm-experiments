package kr.kth.commons.timeperiod;

/**
 * 주차를 계산하기 위한 룰의 종류
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 12. 19.
 */
public enum WeekOfYearRuleKind {

	/**
	 * {@link java.util.Locale} 에 따른 주차 룰
	 */
	Caleandar,

	/**
	 * ISO 8601 규칙 (한주의 시작은 월요일, 한해의 첫번째 주는 그해의 날짜가 4일 이상이 되는 주)
	 */
	Iso8601
}
