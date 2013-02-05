package org.hibernate.search;

import kr.nsoft.data.jpa.domain.JpaEntityBase;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.search.annotations.*;

import javax.persistence.*;

/**
 * org.hibernate.search.Tweet
 * User: sunghyouk.bae@gmail.com
 * Date: 13. 2. 5
 */
@Entity
@Table(name = "Tweet")
@Indexed
public class Tweet extends JpaEntityBase {

    @Id
    @Column(name = "tweet_id")
    @Getter
    private Integer id;

    @Basic
    @Column(name = "user_name")
    @Getter
    @Setter
    private String username;

    @Lob
    @Column(name = "tweet_text")
    @Field(name = "tweet_text", index = Index.YES, store = Store.NO)
    @Boost(3)
    @Getter
    @Setter
    private String text;
}
