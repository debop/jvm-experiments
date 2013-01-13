package kr.kth.timeperiod;

/**
 * kr.kth.timeperiod.MonthKind
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 12. 19.
 */
public enum MonthKind {
    /**
     * 1월
     */
    January(1),

    /**
     * 2월
     */
    Feburary(2),

    /**
     * 3월
     */
    March(3),

    /**
     * 4월
     */
    April(4),

    /**
     * 5월
     */
    May(5),

    /**
     * 6월
     */
    June(6),

    /**
     * 7월
     */
    July(7),

    /**
     * 8월
     */
    August(8),

    /**
     * 9월
     */
    September(9),

    /**
     * 10월
     */
    October(10),

    /**
     * 11월
     */
    November(11),

    /**
     * 12월
     */
    December(12);

    private final int month;

    MonthKind(int month) {
        this.month = month;
    }

    public int toInt() {
        return month;
    }
}
