package org.hibernate.example.domain.model.join;

import kr.ecsp.data.domain.model.EntityBase;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * org.hibernate.example.domain.model.join.Customer
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 11. 19.
 */
@Getter
@Setter
public class Customer extends EntityBase<Long> {

	private static final long serialVersionUID = 9221823986414874215L;

	private String name;
	private String email;
	private Date created;
	private Address address = new Address();

	@Setter(value = AccessLevel.PROTECTED)
	private Date lastUpdated;
}
