package kr.kth.commons.timeperiod.timeline;

import kr.kth.commons.timeperiod.ITimeLineMoment;

import java.util.Comparator;

/**
 * {@link ITimeLineMoment} 비교자
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 12. 22.
 */
public class TimeLineMomentComparer implements Comparator<ITimeLineMoment> {

    @Override
    public int compare(ITimeLineMoment o1, ITimeLineMoment o2) {
        return o1.getMoment().compareTo(o2.getMoment());
    }
}
