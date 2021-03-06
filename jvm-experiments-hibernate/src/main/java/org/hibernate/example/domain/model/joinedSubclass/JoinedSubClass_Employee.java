package org.hibernate.example.domain.model.joinedSubclass;

import com.google.common.base.Objects;
import lombok.Getter;
import lombok.Setter;

/**
 * org.hibernate.example.domain.model.joinedSubclass.JoinedSubClass_Employee
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 12. 2.
 */
public class JoinedSubClass_Employee extends JoinedSubClass_Person {

    private static final long serialVersionUID = -7922814310961001746L;

    @Getter
    @Setter
    private String title;

    @Getter
    @Setter
    private JoinedSubClass_Employee manager;

    @Override
    protected Objects.ToStringHelper buildStringHelper() {
        return super.buildStringHelper()
                .add("title", title);
    }
}
