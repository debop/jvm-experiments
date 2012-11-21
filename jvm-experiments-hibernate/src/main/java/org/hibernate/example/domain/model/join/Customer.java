package org.hibernate.example.domain.model.join;

import com.google.common.base.Objects;
import kr.ecsp.data.domain.model.EntityBase;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * org.hibernate.example.domain.model.join.Customer
 * JpaUser: sunghyouk.bae@gmail.com
 * Date: 12. 11. 19.
 */
@Getter
@Setter
public class Customer extends EntityBase<Long> {

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

	@Setter(value = AccessLevel.PROTECTED)
	private Date lastUpdated;

	@Override
	public int hashCode() {
		if (isPersisted())
			return super.hashCode();

		return Objects.hashCode(name, email);
	}
}
