package kr.kth.data.domain.model;

import java.util.Date;

/**
 * kr.kth.data.domain.model.UpdateTimestampedEntity
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 11. 21.
 */
public interface UpdateTimestampedEntity {

	Date getUpdateTimestamp();

	void updateUpdateTimestamp();
}
