package org.hibernate.example.domain.model;

import kr.ecsp.data.domain.model.EntityBase;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

/**
 * JPA 기본 엔티티 예
 * JpaUser: sunghyouk.bae@gmail.com
 * Date: 12. 11. 19
 */
@Getter
@Setter
@Entity
@Table(name = "STATE_ENTITY")
@DynamicInsert
@DynamicUpdate
@Access(value= AccessType.FIELD)
public class StateEntityImpl extends EntityBase<Long> {

	private static final long serialVersionUID = 6927281191366376283L;

	protected StateEntityImpl() { }
	public StateEntityImpl(final String name) {
		this.name = name;
	}

	@Id
	@GeneratedValue
	@Override
	@Column(name = "ENTITY_ID")
	@Access(value=AccessType.PROPERTY)
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
	@Column(name = "LAST_UPDATED", updatable = false, insertable = false)
	//@Access(value=AccessType.FIELD)
	private Date lastUpdated;

	@Override
	public int hashCode() {
		if(isPersisted())
			return super.hashCode();

		return Objects.hashCode(name);
	}

	@Override
	public String toString() {
		return super.toString() + ", name=[" + name + "]";
	}
}
