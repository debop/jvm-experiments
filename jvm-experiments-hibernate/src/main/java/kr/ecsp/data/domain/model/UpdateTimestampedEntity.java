package kr.ecsp.data.domain.model;

import java.util.Date;

/**
 * kr.ecsp.data.domain.model.UpdateTimestampedEntity
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 11. 21.
 */
public interface UpdateTimestampedEntity {

	Date getUpdateTimestamp();

	void updateLastUpdateTime();
}
