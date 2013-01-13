package kr.kth.commons.tools;

import kr.kth.commons.Guard;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

import static ch.lambdaj.Lambda.maxFrom;
import static ch.lambdaj.Lambda.minFrom;

/**
 * kr.kth.commons.tools.ListTool
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 12. 23.
 */
public final class ListTool {

    private ListTool() {
    }

    /**
     * 컬렉션에서 최소값을 가지는 요소를 구합니다.
     */
    @SuppressWarnings("unchecked")
    public static <T> T min(Collection<? extends Comparable<T>> collection) {
        if (collection == null) return null;

        return (T) minFrom(collection);
    }

    public static <T> T min(Collection<? extends T> coll, Comparator<? super T> comp) {
        if (coll == null) return null;
        return Collections.max(coll, comp);
    }

    /**
     * 컬렉션에서 최대값을 가지는 요소를 구합니다.
     */
    @SuppressWarnings("unchecked")
    public static <T> T max(Collection<? extends Comparable<T>> collection) {
        if (collection == null) return null;

        return (T) maxFrom(collection);
    }

    /**
     * 컬렉션에서 최대값을 구합니다.
     */
    public static <T> T max(Collection<? extends T> coll, Comparator<? super T> comp) {
        Guard.shouldNotBeNull(comp, "comp");
        if (coll == null) return null;

        return Collections.max(coll, comp);
    }
}
