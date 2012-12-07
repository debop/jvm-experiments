package org.hibernate.example.domain.model;

import com.google.common.base.Objects;
import kr.kth.commons.base.ValueObjectBase;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;
import java.util.UUID;

/**
 * 주소 정보
 * JpaUser: sunghyouk.bae@gmail.com
 * Date: 12. 11. 19
 */
@Getter
@Setter
@ToString
public class Address extends ValueObjectBase {

	private static final long serialVersionUID = -4051226015707369322L;

	private String street;
	private String zipcode;
	private String city;

	private Date lastUpdated;
	private UUID systemId;

	@Override
	public int hashCode() {
		return Objects.hashCode(street, zipcode, city);
	}

	@Override
	protected Objects.ToStringHelper buildStringHelper() {
		return super.buildStringHelper()
		            .add("street", street)
		            .add("zipcode", zipcode)
		            .add("city", city);
	}
}

class AddressEx extends Address {

	private String country;

	@Override
	protected Objects.ToStringHelper buildStringHelper() {
		return super.buildStringHelper()
		            .add("country", country);
	}
}
