package org.jpa.example.domain.model.joinedSubclass;

import com.google.common.base.Objects;
import com.google.common.collect.Sets;
import kr.nsoft.commons.tools.HashTool;
import kr.nsoft.data.domain.model.AnnotatedEntityBase;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.Set;

/**
 * org.jpa.example.domain.model.joinedSubclass.Department
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 12. 8.
 */
@Entity
@Table(name = "JS_DEPARTMENT")
public class Department extends AnnotatedEntityBase {

    private static final long serialVersionUID = -414512769734998814L;

    @Id
    @GeneratedValue
    @Column(name = "DEPT_ID")
    private Long id;

    @Column(name = "DEPT_NAME")
    private String name;

    @ManyToOne
    @JoinColumn(name = "COMPANY_ID", nullable = false)
    private Company company;

    @ManyToOne
    @JoinColumn(name = "PARENT_DEPT_ID", nullable = true)
    private Department parent;

    @OneToMany(mappedBy = "parent", cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
    @LazyCollection(value = LazyCollectionOption.EXTRA)
    private Set<Department> children = Sets.newHashSet();

    @Override
    public int hashCode() {
        return isPersisted() ? HashTool.compute(id)
                : HashTool.compute(name, company);

    }

    @Override
    protected Objects.ToStringHelper buildStringHelper() {
        return super.buildStringHelper()
                .add("id", id)
                .add("name", name)
                .add("company", company);
    }
}
