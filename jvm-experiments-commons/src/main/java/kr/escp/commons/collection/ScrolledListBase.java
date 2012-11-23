package kr.escp.commons.collection;

import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * 스크롤되는 목록을 표현하는 클래스입니다.
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 9. 13
 */
@Slf4j
public class ScrolledListBase<E, N extends Comparable<N>> implements ScrolledList<E, N> {

	private static final long serialVersionUID = -5077876937253068976L;

	@Getter
	private final List<E> list;

	@Getter
	private final N lowerBound;

	@Getter
	private final N upperBound;

	public ScrolledListBase(List<E> list, N lowerBound, N upperBound) {

		this.list = Preconditions.checkNotNull(list, "list shoud not be null.");
		this.lowerBound = lowerBound;
		this.upperBound = upperBound;
	}

	public String toString() {
		return
			Objects
				.toStringHelper(this)
				.add("lowerBound", lowerBound)
				.add("upperBound", upperBound)
				.add("list", list)
				.toString();
	}
}
