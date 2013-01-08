package org.jpa.example.domain.model.unionSubclass;

import com.google.common.base.Objects;
import kr.kth.commons.tools.HashTool;
import kr.kth.data.jpa.domain.JpaEntityBase;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

/**
 * org.jpa.example.domain.model.subclass.UnionSubclass_BillingDetails
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 12. 4.
 */
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@DynamicInsert
@DynamicUpdate
public class UnionSubclass_BillingDetails extends JpaEntityBase {

    private static final long serialVersionUID = -972548475181911220L;

    @Id
    @Column(name = "BILLING_DETAILS_ID")
    protected Long id;

    @Column(name = "OWNER", nullable = false)
    private String owner;

    @Override
    public int hashCode() {
        if (isPersisted())
            return HashTool.compute(id);
        return HashTool.compute(owner);
    }

    @Override
    protected Objects.ToStringHelper buildStringHelper() {
        return super.buildStringHelper()
                .add("id", id)
                .add("owner", owner);
    }
}
