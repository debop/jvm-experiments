package org.hibernate.example.domain.model;

import com.google.common.base.Objects;
import kr.kth.commons.timeperiod.tools.TimeTool;
import kr.kth.data.domain.model.EntityBase;
import kr.kth.data.domain.model.IUpdateTimestampedEntity;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class Category extends EntityBase<Long> implements IUpdateTimestampedEntity {

	private static final long serialVersionUID = 7583780980623927361L;

	protected Category() {}

	public Category(String name) {
		this.name = name;
	}

	@Getter @Setter private String name;

	// @Getter private Date lastUpdated = new Date();
	@Getter private Timestamp updateTimestamp;

	public void updateUpdateTimestamp() {
		updateTimestamp = TimeTool.getNowTimestamp();
	}

	@Getter
	private final List<Event> events = new ArrayList<>();

	public void addEvents(Event... ets) {
		for (Event e : ets) {
			events.add(e);
			e.setCategory(this);
		}
	}

	public void removeEvents(Event... ets) {
		for (Event e : ets) {
			events.remove(e);
			e.setCategory(null);
		}
	}

	@Override
	public int hashCode() {
		if (isPersisted())
			return super.hashCode();

		return Objects.hashCode(name);
	}

	@Override
	protected Objects.ToStringHelper buildStringHelper() {
		return super.buildStringHelper()
		            .add("name", name)
		            .add("updateTimestamp", updateTimestamp);
	}
}
