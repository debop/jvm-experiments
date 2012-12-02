package org.hibernate.example.domain.model.join;

import com.google.common.base.Objects;
import kr.kth.data.domain.model.EntityBase;
import kr.kth.data.domain.model.UpdateTimestampedEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * 고객 정보
 * JpaUser: sunghyouk.bae@gmail.com
 * Date: 12. 11. 19.
 */
@Getter
@Setter
public class Customer extends EntityBase<Long> implements UpdateTimestampedEntity {

	private static final long serialVersionUID = 9221823986414874215L;

	protected Customer() {}

	public Customer(String name, String email) {
		this.name = name;
		this.email = email;
	}

	private String name;
	private String email;
	private Date created;

	private Address address = new Address();

	@Setter(value = AccessLevel.PRIVATE)
	private Date updateTimestamp;

	@Override
	public int hashCode() {
		if (isPersisted())
			return super.hashCode();

		return Objects.hashCode(name, email);
	}

	@Override
	public Objects.ToStringHelper buildStringHelper() {
		return super.buildStringHelper()
		            .add("name", name)
		            .add("email", email)
		            .add("created", created);
	}

	@Override
	public void updateUpdateTimestamp() {
		this.updateTimestamp = new Date();
	}
}
