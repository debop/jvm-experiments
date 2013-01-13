package kr.kth.timeperiod;

/**
 * 주차 계산 시, 한 해의 첫번째 주를 판단하는 규칙
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 12. 27.
 */
public enum CalendarWeekRule {

    /**
     * 1월 1일이 속한 주(Week)가 한 해의 첫번째 주가 된다. (한국, 미국은 이 기준을 따른다)
     */
    FirstDay("FirstDay"),
    /**
     * 한 주의 4일 이상이 같은 해에 앴는 첫번째 주를 첫주로 한다. ISO 8601이 이 규칙이다. (유럽 등 대부분의 나라가 따른다)
     */
    FirstFullWeek("FirstFullWeek"),
    /**
     * 한 주의 모든 요일이 같은 년도(Year)인 첫번째 주를 한 해의 첫주로 한다.
     */
    FirstFourDayWeek("FirstFourDayWeek");

    private final String rule;

    CalendarWeekRule(String rule) {
        this.rule = rule;
    }

    public int toInt() {
        return this.ordinal();
    }
}
