package org.hibernate.example.domain.model;

import com.google.common.base.Objects;
import kr.escp.commons.core.ValueObjectBase;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 설명을 추가하세요.
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

	@Override
	public int hashCode() {
		return Objects.hashCode(street, zipcode, city);
	}
}
