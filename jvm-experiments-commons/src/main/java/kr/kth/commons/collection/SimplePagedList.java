package kr.kth.commons.collection;

import kr.kth.commons.base.Guard;
import lombok.Getter;

import java.util.List;

/**
 * 페이징된 목록을 표현하는 클래스입니다.
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 9. 13
 */
@Getter
public class SimplePagedList<E> implements PagedList<E> {

	private static final long serialVersionUID = -5027718652421583413L;

	private final List<E> list;
	private final int pageNo;
	private final int pageSize;
	private final long itemCount;
	private final long pageCount;

	public SimplePagedList(List<E> list, int pageNo, int pageSize, long itemCount) {

		Guard.shouldNotBeNull(list, "list");
		Guard.shouldBePositiveNumber(pageNo, "pageNo");
		Guard.shouldBePositiveNumber(pageSize, "pageSize");
		Guard.shouldNotBeNegativeNumber(itemCount, "itemCount");

		this.list = list;
		this.pageNo = pageNo;
		this.pageSize = pageSize;
		this.itemCount = itemCount;

		this.pageCount = (long) (itemCount / pageSize) + ((itemCount % pageSize) > 0 ? 1 : 0);
	}
}
