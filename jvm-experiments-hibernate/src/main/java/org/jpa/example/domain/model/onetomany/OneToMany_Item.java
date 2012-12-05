package org.jpa.example.domain.model.onetomany;

import com.google.common.base.Objects;
import kr.kth.commons.tools.HashTool;
import kr.kth.data.jpa.domain.JpaEntityBase;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * org.jpa.example.domain.model.onetomany.OneToMany_Item
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 12. 4.
 */
@Entity
@Table(name = "JPA_ONE_TO_MANY_ITEM")
public class OneToMany_Item extends JpaEntityBase {

	private static final long serialVersionUID = -6749071403586052068L;

	@Id
	@GeneratedValue
	@Column(name = "ITEM_ID")
	private Long id;

	@Column(name = "NAME")
	private String name;

	@Column(name = "DESCRIPTION")
	private String description;

	@OneToMany(mappedBy = "item", cascade = { CascadeType.ALL }, fetch = FetchType.LAZY)
	@LazyCollection(value = LazyCollectionOption.EXTRA)
	private Set<OneToMany_Bid> bids = new HashSet<OneToMany_Bid>();

	@Override
	public int hashCode() {
		if (isPersisted())
			return HashTool.compute(id);

		return HashTool.compute(name);
	}

	@Override
	protected Objects.ToStringHelper buildStringHelper() {
		return super.buildStringHelper()
		            .add("id", id)
		            .add("name", name)
		            .add("description", description);
	}
}
