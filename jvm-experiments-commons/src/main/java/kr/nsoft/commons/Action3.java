package kr.nsoft.commons;

/**
 * 인자 3개를 받고, void 형을 반환하는 메소드를 가진 인터페이스
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 9. 27.
 */
public interface Action3<T1, T2, T3> {

    public void perform(T1 arg1, T2 arg2, T3 arg3);
}
