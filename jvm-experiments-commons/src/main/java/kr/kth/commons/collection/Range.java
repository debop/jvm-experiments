package kr.kth.commons.collection;

import java.util.Iterator;

/**
 * 특정 범위에 해당하는 숫자들을 열거하도록 합니다.
 * User: sunghyouk.bae@gmail.com
 * Date: 13. 1. 11
 */
public abstract class Range<T extends Number> implements Iterable<T> {
    private Range() { }

    public static Range<Integer> range(int from, int to, int step) {
        return new IntRange(from, to, step);
    }

    public static Range<Integer> range(int from, int to) {
        return new IntRange(from, to, to >= from ? 1 : -1);
    }

    public static Range<Integer> range(int to) {
        return new IntRange(0, to, 1);
    }

    public static Range<Short> range(short from, short to, short step) {
        return new ShortRange(from, to, step);
    }

    public static Range<Short> range(short from, short to) {
        return new ShortRange(from, to, (short) (to >= from ? 1 : -1));
    }

    public static Range<Short> range(short to) {
        return new ShortRange((short) 0, to, (short) 1);
    }

    public static Range<Long> range(long from, long to, long end) {
        return new LongRange(from, to, end);
    }

    public static Range<Long> range(long from, long to) {
        return new LongRange(from, to, to >= from ? 1L : -1L);
    }

    public static Range<Long> range(Long to) {
        return new LongRange(0L, to, 1L);
    }

    protected static class IntRange extends Range<Integer> implements Iterator<Integer> {
        protected int lowerBound, step, upperBound;

        public IntRange(int fromInclude, int toExclude, int step) {
            this.lowerBound = fromInclude;
            this.step = step;
            this.upperBound = toExclude;
        }

        public boolean hasNext() {
            return step > 0 ? lowerBound < upperBound : upperBound < lowerBound;
        }

        public Integer next() {
            int res = lowerBound;
            lowerBound += step;
            return res;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Iterator<Integer> iterator() {
            return this;
        }

    }

    protected static class LongRange extends Range<Long> implements Iterator<Long> {
        protected long m_cur, m_step, m_end;

        public LongRange(long from, long to, long step) {
            m_cur = from;
            m_step = step;
            m_end = to;
        }

        public boolean hasNext() {
            return m_step > 0 ? m_cur < m_end : m_end < m_cur;
        }

        public Long next() {
            long res = m_cur;
            m_cur += m_step;
            return res;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Iterator<Long> iterator() {
            return this;
        }
    }

    protected static class ShortRange extends Range<Short> implements Iterator<Short> {
        protected short m_cur, m_step, m_end;

        public ShortRange(short from, short to, short step) {
            m_cur = from;
            m_step = step;
            m_end = to;
        }

        public boolean hasNext() {
            return m_step > 0 ? m_cur < m_end : m_end < m_cur;
        }

        public Short next() {
            short res = m_cur;
            m_cur += m_step;
            return res;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Iterator<Short> iterator() {
            return this;
        }
    }
}
