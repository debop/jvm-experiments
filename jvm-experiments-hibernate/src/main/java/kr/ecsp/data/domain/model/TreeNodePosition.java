package kr.ecsp.data.domain.model;

import com.google.common.base.Objects;
import kr.escp.commons.Guard;
import kr.escp.commons.ValueObjectBase;
import kr.escp.commons.tools.HashTool;
import lombok.Getter;
import lombok.Setter;

/**
 * 트리 형식의 자료 구조의 하나의 노드의 위치를 나타냅니다.
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 9. 13.
 */
@Getter
@Setter
public class TreeNodePosition extends ValueObjectBase {

	private static final long serialVersionUID = 7724440843329055902L;

	private Integer level;
	private Integer order;

	public TreeNodePosition() {
		level = 0;
		order = 0;
	}

	public TreeNodePosition(Integer level, Integer order) {
		this.level = level;
		this.order = order;
	}

	public TreeNodePosition(TreeNodePosition nodePosition) {
		Guard.shouldNotBeNull(nodePosition, "nodePosition");
		this.level = nodePosition.level;
		this.order = nodePosition.order;
	}

	public void setPosition(int level, int order) {
		this.level = level;
		this.order = order;
	}

	public void setPosition(TreeNodePosition nodePosition) {
		Guard.shouldNotBeNull(nodePosition, "nodePosition");
		this.level = nodePosition.level;
		this.order = nodePosition.order;
	}

	@Override
	public int hashCode() {
		return HashTool.compute(level, order);
	}

	@Override
	protected Objects.ToStringHelper buildStringHelper() {
		return super.buildStringHelper()
		            .add("level", level)
		            .add("order", order);
	}
}
