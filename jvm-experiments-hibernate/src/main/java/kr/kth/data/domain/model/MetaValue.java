package kr.kth.data.domain.model;

import kr.kth.commons.base.IValueObject;

/**
 * 메타 정보를 표현하는 인터페이스입니다.
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 9. 18.
 */
public interface MetaValue extends IValueObject {

	String getValue();

	void setValue(String value);

	String getLabel();

	void setLabel(String label);

	String getDescription();

	void setDescription(String description);

	String getExAttr();

	void setExAttr(String exAttr);
}
