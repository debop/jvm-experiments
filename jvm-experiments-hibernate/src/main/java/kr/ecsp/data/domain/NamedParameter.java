package kr.ecsp.data.domain;

import java.io.Serializable;

/**
 * 설명을 추가하세요.
 * JpaUser: sunghyouk.bae@gmail.com
 * Date: 12. 11. 19
 */
public interface NamedParameter extends Serializable {

	String getName();

	void setName(String name);

	Object getValue();

	void setValue(Object value);
}
