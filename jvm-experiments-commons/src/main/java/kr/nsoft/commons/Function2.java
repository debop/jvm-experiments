package kr.nsoft.commons;

/**
 * 인자 2개를 받고, 결과를 반환하는 메소드를 가진 인터페이스
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 9. 27.
 */
public interface Function2<T1, T2, R> {

    R execute(T1 arg1, T2 arg2);
}
