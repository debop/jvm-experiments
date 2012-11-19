package org.hibernate.example.domain.model;

import com.google.common.base.Objects;
import kr.ecsp.data.domain.model.ValueObjectBase;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * 설명을 추가하세요.
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 11. 19
 */
@Embeddable
@Getter
@Setter
public class Address extends ValueObjectBase {

	private static final long serialVersionUID = -4051226015707369322L;

	@Column(name="STREET", length=128)
	private String street;

	@Column(name="ZIPCODE", length=12)
	private String zipcode;

	@Column(name="CITY", length=36)
	private String city;

	@Override
	public int hashCode() {
		return Objects.hashCode(street, zipcode, city);
	}
}
