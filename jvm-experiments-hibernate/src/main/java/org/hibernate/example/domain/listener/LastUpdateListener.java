package org.hibernate.example.domain.listener;

import lombok.extern.slf4j.Slf4j;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

/**
 * org.hibernate.example.domain.listener.LastUpdateListener
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 11. 21.
 */
@Slf4j
public class LastUpdateListener {

	@PreUpdate
	@PrePersist
	public void setLastUpdate() {
		if (log.isDebugEnabled())
			log.debug("구현 중입니다.");
	}
}
