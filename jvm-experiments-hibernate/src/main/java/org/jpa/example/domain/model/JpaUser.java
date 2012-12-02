package org.jpa.example.domain.model;

import com.google.common.base.Objects;
import kr.kth.data.domain.model.StatefulEntityBase;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Index;

import javax.persistence.*;

/**
 * org.jpa.example.domain.model.JpaUser
 * JpaUser: sunghyouk.bae@gmail.com
 * Date: 12. 11. 20.
 */
@Entity
@Table(name = "JPA_USERS")
@Cacheable
@DynamicInsert
@DynamicUpdate
@Getter
@Setter
public class JpaUser extends StatefulEntityBase {

	private static final long serialVersionUID = -4278711858304883834L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "USER_ID")
	private Long id;

	@Column(name = "FIRST_NAME")
	private String firstname;

	@Column(name = "LAST_NAME")
	private String lastname;

	@Column(name = "USER_NAME", length = 128, nullable = false)
	@Index(name = "IX_USER_USER_NAME")
	private String username;

	@Column(name = "USER_PWD", length = 64, nullable = false)
	@Index(name = "IX_USER_USER_NAME")
	private String password;

	@Column(name = "USER_EMAIL")
	private String email;

	@Embedded
	@AttributeOverrides(
		{
			@AttributeOverride(name = "street", column = @Column(name = "HOME_STREET", length = 128)),
			@AttributeOverride(name = "zipcode", column = @Column(name = "HOME_ZIPCODE", length = 24)),
			@AttributeOverride(name = "city", column = @Column(name = "HOME_CITY", length = 128)),
		}
	)
	private Address homeAddress = new Address();

	@Embedded
	@AttributeOverrides(
		{
			@AttributeOverride(name = "street", column = @Column(name = "OFFICE_STREET", length = 128)),
			@AttributeOverride(name = "zipcode", column = @Column(name = "OFFICE_ZIPCODE", length = 24)),
			@AttributeOverride(name = "city", column = @Column(name = "OFFICE_CITY", length = 128)),
		}
	)
	private Address officeAddress = new Address();

	@Override
	public int hashCode() {
		if (isPersisted())
			return super.hashCode();

		return Objects.hashCode(username, password);
	}

	@Override
	protected Objects.ToStringHelper buildStringHelper() {
		return super.buildStringHelper()
		            .add("firstname", firstname)
		            .add("lastname", lastname)
		            .add("username", username)
		            .add("userpwd", password)
		            .add("userEmail", email)
		            .add("homeAddress", homeAddress)
		            .add("officeAddress", officeAddress);
	}
}
