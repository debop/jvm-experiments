package org.jpa.example.domain.model;

import com.google.common.base.Objects;
import kr.kth.commons.base.ValueObjectBase;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * org.jpa.example.domain.model.JpaAddress
 * JpaUser: sunghyouk.bae@gmail.com
 * Date: 12. 11. 20.
 */
@Getter
@Setter
@ToString
@Embeddable
public class JpaAddress extends ValueObjectBase {

	private static final long serialVersionUID = 2162684426050522828L;

	@Column(name = "STREET", length = 128)
	private String street;

	@Column(name = "ZIPCODE", length = 12)
	private String zipcode;

	@Column(name = "CITY", length = 64)
	private String city;

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
