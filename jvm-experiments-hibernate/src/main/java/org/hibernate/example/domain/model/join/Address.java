package org.hibernate.example.domain.model.join;

import com.google.common.base.Objects;
import kr.kth.commons.base.ValueObjectBase;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 주소 정보
 * JpaUser: sunghyouk.bae@gmail.com
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

	@Override
	public Objects.ToStringHelper buildStringHelper() {
		return super.buildStringHelper()
		            .add("street", street)
		            .add("zipcode", zipcode)
		            .add("city", city);
	}
}
