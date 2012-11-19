package org.hibernate.example.domain.model.join;

import com.google.common.base.Objects;
import kr.ecsp.data.domain.model.ValueObjectBase;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * org.hibernate.example.domain.model.join.Address
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 11. 19.
 */
@Getter
@Setter
@ToString
public class Address extends ValueObjectBase {

	private static final long serialVersionUID = 4556469620637965297L;
	private String street;

	private String zipcode;

	private String city;

	@Override
	public int hashCode() {
		return Objects.hashCode(street, zipcode, city);
	}
}
