package kr.escp.commons.collection;

import java.io.Serializable;
import java.util.List;

/**
 * 페이지 처리된 목록을 표현하는 인터페이스
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 9. 12
 */
public interface PagedList<E> extends Serializable {

	List<E> getList();

	int getPageNo();

	int getPageSize();

	long getPageCount();

	long getItemCount();
}
