package org.jpa.example.domain.model.onetomany;

import com.google.common.base.Objects;
import kr.kth.commons.tools.HashTool;
import kr.kth.data.jpa.domain.JpaEntityBase;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.example.domain.model.collection.Item;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * org.jpa.example.domain.model.onetomany.OneToMany_Bid
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 12. 4.
 */
@Entity
@Table(name = "JPA_ONE_TO_MANY_BID")
@DynamicInsert
@DynamicUpdate
public class OneToMany_Bid extends JpaEntityBase {

    private static final long serialVersionUID = 2401312144738936609L;

    @Id
    @GeneratedValue
    @Column(name = "BID_ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "ITEM_ID", nullable = false)
    private Item item;

    @Column(name = "AMOUNT", nullable = false)
    private BigDecimal amount;

    @Transient
    private Timestamp timestamp;

    @Override
    public int hashCode() {
        if (isPersisted())
            return HashTool.compute(id);

        return HashTool.compute(id);
    }

    @Override
    protected Objects.ToStringHelper buildStringHelper() {
        return super.buildStringHelper()
                .add("id", id)
                .add("amount", amount)
                .add("item", item);
    }
}
