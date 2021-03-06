package kr.nsoft.commons.collection;

import java.util.Date;
import java.util.List;

/**
 * 스크롤되는 목록을 표현하는 클래스입니다.
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 9. 13
 */
public class TimeScrolledList<E> extends ScrolledListBase<E, Date> {

    private static final long serialVersionUID = 8043558335662744201L;

    public TimeScrolledList(List<E> list, Date lowerBound, Date upperBound) {
        super(list, lowerBound, upperBound);
    }
}
