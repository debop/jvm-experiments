package org.jpa.example.domain.model.joinedSubclass;

import com.google.common.base.Objects;
import com.google.common.collect.Sets;
import kr.nsoft.commons.tools.HashTool;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.Set;

/**
 * org.jpa.example.domain.model.joinedSubclass.Employee
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 12. 8.
 */
@Entity
@Table(name = "JS_EMPLOYEE")
public class Employee extends PersonBase {

    private static final long serialVersionUID = 8602281294123499250L;

    @Column(name = "EMP_TITLE")
    private String title;

    @ManyToOne
    @JoinColumn(name = "DEPT_ID", nullable = false)
    private Department department;

    @ManyToOne
    @JoinColumn(name = "MANAGER_ID")
    private Employee manager;

    @OneToMany(mappedBy = "manager", cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
    @LazyCollection(value = LazyCollectionOption.EXTRA)
    private Set<Employee> members = Sets.newHashSet();

    @Override
    public int hashCode() {
        if (isPersisted())
            return super.hashCode();

        return HashTool.compute(super.hashCode(), department, manager);
    }

    @Override
    protected Objects.ToStringHelper buildStringHelper() {
        return super.buildStringHelper()
                .add("title", title)
                .add("department", department)
                .add("manager", manager);
    }
}
