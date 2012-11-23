package kr.escp.commons.collection;

import java.io.Serializable;
import java.util.List;

/**
 * 설명을 추가하세요.
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 9. 12
 */
public interface ScrolledList<E, N extends Comparable<N>> extends Serializable {

	/**
	 * 스크롤 영역의 목록
	 *
	 * @return
	 */
	List<E> getList();

	/**
	 * 스크롤 영역의 하한 값
	 *
	 * @return
	 */
	N getLowerBound();

	/**
	 * 스크롤 영역의 상한 값
	 *
	 * @return
	 */
	N getUpperBound();

}
