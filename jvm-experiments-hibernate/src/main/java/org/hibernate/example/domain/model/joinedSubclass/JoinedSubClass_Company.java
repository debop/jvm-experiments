package org.hibernate.example.domain.model.joinedSubclass;

import com.google.common.base.Objects;
import kr.kth.commons.tools.HashTool;
import kr.kth.data.domain.model.EntityBase;
import lombok.Getter;
import lombok.Setter;

/**
 * org.hibernate.example.domain.model.joinedSubclass.JoinedSubClass_Company.hbm.xml
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 12. 2.
 */
public class JoinedSubClass_Company extends EntityBase<Long> {

    private static final long serialVersionUID = 8028783232908962980L;

    @Getter
    @Setter
    private String name;


    @Override
    public int hashCode() {
        if (isPersisted())
            return super.hashCode();

        return HashTool.compute(name);
    }

    @Override
    protected Objects.ToStringHelper buildStringHelper() {
        return super.buildStringHelper()
                .add("name", name);
    }
}
