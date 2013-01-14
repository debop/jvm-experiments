package kr.kth.timeperiod

/**
 * kr.kth.timeperiod.CalendarWeekRule
 * User: sunghyouk.bae@gmail.com
 * Date: 13. 1. 14
 */
object CalendarWeekRule extends Enumeration {

  type CalendarWeekRule = Value
  /**
   * 1월 1일이 속한 주(Week)가 한 해의 첫번째 주가 된다. (한국, 미국은 이 기준을 따른다)
   */
  val FirstDay = Value("FirstDay")
  /**
   * 한 주의 4일 이상이 같은 해에 앴는 첫번째 주를 첫주로 한다. ISO 8601이 이 규칙이다. (유럽 등 대부분의 나라가 따른다)
   */
  val FirstFullWeek = Value("FirstFullWeek")
  /**
   * 한 주의 모든 요일이 같은 년도(Year)인 첫번째 주를 한 해의 첫주로 한다.
   */
  val FirstFourDayWeek = Value("FirstFourDayWeek")
}
