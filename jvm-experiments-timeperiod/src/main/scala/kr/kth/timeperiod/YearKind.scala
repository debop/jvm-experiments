package kr.kth.timeperiod

/**
 * 년의 종류 - 년의 종류에 따라 시작 월이 달라집니다.
 * User: sunghyouk.bae@gmail.com
 * Date: 13. 1. 13.
 */
object YearKind extends Enumeration {
    type YearKind = Value

    /**
     * 설치된 OS의 문화권에 해당하는 Year
     */
    val System = Value("System")
    /**
     * 현 ThreadContext에 지정된 {@link java.util.Locale}의 Calendar의 Year
     */
    val Calendar = Value("Calendar")
    /**
     * 회계 년 (2월 시작)
     */
    val Fiscal = Value("Fiscal")
    /**
     * 교육 년 (3월 시작, 9월 시작)
     */
    val School = Value("School")
    /**
     * 사용자 정의 Year
     */
    val Custom = Value("Custom")
}
