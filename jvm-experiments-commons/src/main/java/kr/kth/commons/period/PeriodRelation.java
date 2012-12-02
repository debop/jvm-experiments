package kr.kth.commons.period;

/**
 * 설명을 추가하세요.
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 9. 18
 */
public enum PeriodRelation {

	NoRelation,
	After,
	StartTouching,
	StartInside,
	InsideStartTouching,
	EnclosingStartTouching,
	Enclosing,
	EnclosingEndTouching,
	ExactMatch,
	Inside,
	InsideEndTouching,
	EndInside,
	EndTouching,
	Before
}
