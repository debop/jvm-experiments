package org.jpa.example.domain.model.onetoone;

import com.google.common.base.Objects;
import kr.nsoft.commons.tools.HashTool;
import kr.nsoft.data.jpa.domain.JpaEntityBase;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * org.jpa.example.domain.model.onetoone.OneToOne_Item
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 12. 4.
 */
@Entity
@Table(name = "JPA_ONE_TO_ONE_ITEM")
@Getter
@Setter
public class OneToOne_Item extends JpaEntityBase {

    private static final long serialVersionUID = -7262024786572110211L;

    @Id
    @GeneratedValue
    @Column(name = "ITEM_ID")
    private Long id;

    @Column(name = "ITEM_NAME")
    private String name;

    @Column(name = "ITEM_DESC")
    private String description;

    @Column(name = "ITEM_INIT_PRICE")
    private BigDecimal initialPrice;

    @Override
    public int hashCode() {
        if (isPersisted())
            return HashTool.compute(id);

        return HashTool.compute(name);
    }

    @Override
    protected Objects.ToStringHelper buildStringHelper() {
        return super.buildStringHelper()
                .add("id", id)
                .add("name", name)
                .add("description", description)
                .add("initialPrice", initialPrice);
    }
}
