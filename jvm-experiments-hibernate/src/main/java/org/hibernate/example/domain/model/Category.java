package org.hibernate.example.domain.model;

import com.google.common.base.Objects;
import kr.ecsp.data.domain.model.EntityBase;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

public class Category extends EntityBase<Long> {

	private static final long serialVersionUID = 7583780980623927361L;

	@Getter @Setter private String name;

	@Getter(lazy = true)
	private final List<Event> events = new ArrayList<>();

	@Override
	public int hashCode() {
		if (isPersisted())
			return super.hashCode();

		return Objects.hashCode(name);
	}

	@Override
	public String toString() {
		return Objects.toStringHelper(this)
		              .add("id", id)
		              .add("name", name)
		              .toString();
	}
}
