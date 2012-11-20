package org.hibernate.example.domain.model;

import com.google.common.base.Objects;
import kr.ecsp.data.domain.model.EntityBase;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * org.hibernate.example.domain.model.JpaUser
 * JpaUser: sunghyouk.bae@gmail.com
 * Date: 12. 11. 19.
 */
@Getter
@Setter
@ToString
public class User extends EntityBase<Long> {

	private static final long serialVersionUID = 6892074618286797904L;

	private Long id;

	private String firstname;
	private String lastname;
	public String username;
	private String userpwd;
	private String userEmail;

	private Address homeAddress = new Address();
	private Address officeAddress = new Address();

	@Override
	public int hashCode() {
		if(isPersisted())
			return super.hashCode();

		return Objects.hashCode(username, userpwd);
	}
}
