package kr.kth.timeperiod;

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

    public int toInt() {
        return quarter;
    }

    public static QuarterKind valueOf(Integer quarter) {
        if (quarter == null)
            return null;
        switch (quarter) {
            case 1:
                return QuarterKind.First;
            case 2:
                return QuarterKind.Second;
            case 3:
                return QuarterKind.Third;
            case 4:
                return QuarterKind.Fouth;
            default:
                throw new RuntimeException("지원히지 않는 값입니다. quarter=" + quarter);
        }
    }
}
