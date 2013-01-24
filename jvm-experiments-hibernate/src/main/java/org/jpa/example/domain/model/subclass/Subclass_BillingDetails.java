package org.jpa.example.domain.model.subclass;

import com.google.common.base.Objects;
import kr.nsoft.commons.tools.HashTool;
import kr.nsoft.data.jpa.domain.JpaEntityBase;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

/**
 * org.jpa.example.domain.model.subclass.Subclass_BillingDetails
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 12. 4.
 */
@Entity
@Table(name = "JPA_SUBCLASS_BILLING_DETAILS")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "BILLING_TYPE", discriminatorType = DiscriminatorType.STRING)
@DynamicInsert
@DynamicUpdate
public class Subclass_BillingDetails extends JpaEntityBase {

    private static final long serialVersionUID = -972548475181911220L;

    @Id
    @GeneratedValue
    @Column(name = "BILLING_DETAILS_ID")
    private Long id;

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
