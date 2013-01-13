package kr.kth.timeperiod;

/**
 * Date 검색 시, 해당 검색이 "완료 경계까지" 인가, 그 다음에 해당하는 부분인가를 정의한다.
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 12. 19.
 */
public enum SeekBoundaryMode {
    /**
     * Date 검색 시 검색한 값을 반환하도록 한다.
     */
    Fill,
    /**
     * Date 검색 시, 검색한 다음 값을 반환하도록 합니다.
     */
    Next
}
