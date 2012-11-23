package kr.escp.commons;

/**
 * 인자 1개를 받고, 결과를 반환하는 메소드를 가진 인터페이스
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 9. 27.
 */
public interface Func1<T, R> {

	R execute(T arg);
}
