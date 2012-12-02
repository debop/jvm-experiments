package kr.kth.data.domain.model;

import java.io.Serializable;

/**
 * 설명을 추가하세요.
 * JpaUser: sunghyouk.bae@gmail.com
 * Date: 12. 11. 19
 */
public interface Entity<TId extends Serializable> {

	<TId> TId getId();
}
