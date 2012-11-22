package org.hibernate.example.domain.model;

import com.google.common.base.Objects;
import com.google.common.base.Strings;
import kr.ecsp.data.domain.model.EntityBase;
import kr.ecsp.data.domain.model.UpdateTimestampedEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class Event extends EntityBase<Long> implements UpdateTimestampedEntity {

	private static final long serialVersionUID = 930773110476290116L;

	public Event() {}

	public Event(String title, Date date) {

		assert !Strings.isNullOrEmpty(title);

		this.title = title;
		this.date = date;
	}

	private Date date;
	private String title;
	private Category category;

	@Setter(value = AccessLevel.PROTECTED)
	private Date updateTimestamp;

	public void updateLastUpdateTime() {
		updateTimestamp = new Date();
	}


	@Override
	public int hashCode() {
		if (isPersisted())
			return super.hashCode();
		return Objects.hashCode(title, date);
	}

	@Override
	public String toString() {
		return Objects.toStringHelper(this)
		              .add("id", id)
		              .add("title", title)
		              .add("date", date)
		              .add("updateTimestamp", updateTimestamp)
		              .add("category", category)
		              .toString();
	}
}

