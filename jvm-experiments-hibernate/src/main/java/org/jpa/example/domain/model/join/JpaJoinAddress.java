package org.jpa.example.domain.model.join;

import kr.kth.commons.base.ValueObjectBase;
import lombok.Data;

import javax.persistence.Embeddable;

/**
 * org.jpa.example.domain.model.join.JpaJoinAddress
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 12. 5.
 */
@Data
@Embeddable
public class JpaJoinAddress extends ValueObjectBase {

	private String street;

	private String zipcode;

	private String city;
}
