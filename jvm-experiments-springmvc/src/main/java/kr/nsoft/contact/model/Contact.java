package kr.nsoft.contact.model;

import com.google.common.base.Objects;
import kr.nsoft.commons.tools.HashTool;
import kr.nsoft.data.domain.model.AnnotatedEntityBase;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.search.annotations.*;

import javax.persistence.*;

/**
 * kr.nsoft.contact.model.Contact
 * User: sunghyouk.bae@gmail.com
 * Date: 13. 2. 6
 */
@Entity
@Table(name = "MVC_CONTACTS")
@Indexed
public class Contact extends AnnotatedEntityBase {

    @Getter
    @Id
    @GeneratedValue
    @Column(name = "CONTACT_ID")
    private Long id;

    @Getter
    @Setter
    @Column(name = "FIRSTNAME", length = 128)
    @Field
    private String firstname;

    @Getter
    @Setter
    @Column(name = "LASTNAME", length = 128)
    @Field
    private String lastname;

    @Getter
    @Setter
    @Column(name = "EMAIL", length = 64)
    @Field
    private String email;

    @Getter
    @Setter
    @Column(name = "TELEPHONE", length = 48)
    private String telephone;

    @Getter
    @Setter
    @Column(name = "BIOGRAPHY", length = 2048)
    @Field(index = Index.YES, store = Store.NO, analyze = Analyze.YES)
    @Boost(3)
    private String bio;

    @Override
    public int hashCode() {
        if (isPersisted())
            return HashTool.compute(id);

        return HashTool.compute(firstname, lastname, email);
    }

    @Override
    protected Objects.ToStringHelper buildStringHelper() {
        return super.buildStringHelper()
                .add("id", id)
                .add("firstname", firstname)
                .add("lastname", lastname)
                .add("email", email);
    }
}
