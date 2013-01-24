package org.hibernate.example.domain.model.joinedSubclass;

import com.google.common.base.Objects;
import kr.nsoft.commons.tools.HashTool;
import kr.nsoft.data.domain.model.EntityBase;
import lombok.Getter;
import lombok.Setter;

/**
 * org.hibernate.example.domain.model.joinedSubclass.JoinedSubClass_Person
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 12. 2.
 */
public abstract class JoinedSubClass_Person extends EntityBase<Long> {

    private static final long serialVersionUID = -7965446416537277249L;

    @Getter
    @Setter
    private JoinedSubClass_Company company;

    @Getter
    @Setter
    private String name;

    @Override
    public int hashCode() {
        return isPersisted() ? super.hashCode()
                : HashTool.compute(name);

    }

    @Override
    protected Objects.ToStringHelper buildStringHelper() {
        return super.buildStringHelper()
                .add("name", name);
    }
}
