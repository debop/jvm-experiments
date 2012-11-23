package kr.ecsp.data.hibernate.listener;

import kr.ecsp.data.domain.model.UpdateTimestampedEntity;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.event.spi.PreInsertEvent;
import org.hibernate.event.spi.PreInsertEventListener;
import org.hibernate.event.spi.PreUpdateEvent;
import org.hibernate.event.spi.PreUpdateEventListener;

/**
 * {@link UpdateTimestampedEntity} 를 구현한 엔티티의 updateTimestamp 값을 엔티티 저장 시에 갱신해 주는 Event Listener 입니다.
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 11. 22.
 */
@Slf4j
public class UpdateTimestampedEventListener implements PreInsertEventListener, PreUpdateEventListener {

	private static final long serialVersionUID = -7472589588444503777L;

	public UpdateTimestampedEventListener() {
		if (UpdateTimestampedEventListener.log.isDebugEnabled())
			UpdateTimestampedEventListener.log.debug("UpdateTimestampedEventListener 생성");
	}

	@Override
	public boolean onPreInsert(PreInsertEvent event) {
		Object entity = event.getEntity();
		if (entity instanceof UpdateTimestampedEntity) {
			((UpdateTimestampedEntity) entity).updateUpdateTimestamp();
			if (UpdateTimestampedEventListener.log.isDebugEnabled())
				UpdateTimestampedEventListener.log.debug("UpdateTimestampedEntity의 updateTimestamp 값을 설정했습니다.");
		}
		return false;
	}

	@Override
	public boolean onPreUpdate(PreUpdateEvent event) {

		if (UpdateTimestampedEventListener.log.isDebugEnabled())
			UpdateTimestampedEventListener.log.debug("onPreUpdate 호출. PersistEvent=[{}]", event);

		Object entity = event.getEntity();
		if (entity instanceof UpdateTimestampedEntity) {
			((UpdateTimestampedEntity) entity).updateUpdateTimestamp();
			if (UpdateTimestampedEventListener.log.isDebugEnabled())
				UpdateTimestampedEventListener.log.debug("UpdateTimestampedEntity의 updateTimestamp 값을 설정했습니다.");
		}
		return false;
	}
}
