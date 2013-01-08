package kr.kth.commons.timeperiod;

/**
 * 한해를 반기로 나눠 전반기/후반기로 표현합니다.
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 12. 19.
 */
public enum HalfYearKind {
    /**
     * 전반기
     */
    First(1),
    /**
     * 후반기
     */
    Second(2);

    private final int halfYear;

    HalfYearKind(int halfYear) {
        this.halfYear = halfYear;
    }

    public final int toInt() {
        return halfYear;
    }

    public static HalfYearKind valueOf(int value) {
        return (value == 2) ? Second : First;
    }
}
