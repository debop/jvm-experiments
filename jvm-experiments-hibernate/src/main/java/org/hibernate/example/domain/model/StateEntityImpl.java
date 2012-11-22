package org.hibernate.example.domain.model;

import com.google.common.base.Objects;
import kr.ecsp.data.domain.model.EntityBase;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;

import javax.persistence.*;
import java.util.Date;


/**
 * JPA 기본 엔티티 예
 * JpaUser: sunghyouk.bae@gmail.com
 * Date: 12. 11. 19
 */
@Slf4j
@Getter
@Setter
@Entity
@Table(name = "STATE_ENTITY")
public class StateEntityImpl extends EntityBase<Long> {

	private static final long serialVersionUID = 6927281191366376283L;

	protected StateEntityImpl() {
	}

	public StateEntityImpl(final String name) {
		this.name = name;
	}

	@Id
	@GeneratedValue
	@Override
	@Column(name = "ENTITY_ID")
	@Access(value = AccessType.PROPERTY)
	public Long getId() {
		return super.getId();
	}

	@Override
	protected void setId(Long id) {
		super.setId(id);
	}

	@Column(name = "STATE_NAME", nullable = false, length = 128)
	@org.hibernate.annotations.Index(name = "IX_STATE_ENTITY_NAME")
	//@Access(value=AccessType.FIELD)
	private String name;

	@Temporal(value = TemporalType.TIMESTAMP)
	@Column(name = "LAST_UPDATED", insertable = false, updatable = false)
	@Generated(value = GenerationTime.ALWAYS)
	@Getter
	private Date lastUpdated;

	@PrePersist
	@PreUpdate
	protected void updateLastUpdated() {

		if (log.isDebugEnabled())
			log.debug("PrePersist, PreUpdate event 발생...");

		lastUpdated = new Date();
	}

	@Override
	public int hashCode() {
		if (isPersisted())
			return super.hashCode();

		return Objects.hashCode(name);
	}

	@Override
	public String toString() {
		return Objects.toStringHelper(this)
		              .add("id", id)
		              .add("name", name)
		              .add("lastUpdated", lastUpdated)
		              .toString();
	}
}
