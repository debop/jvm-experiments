package kr.nsoft.commons.io;

import com.google.common.base.Objects;
import com.google.common.collect.Lists;
import kr.nsoft.commons.ValueObjectBase;
import kr.nsoft.commons.tools.HashTool;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


/**
 * pudding.pudding.commons.core.io.Company
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 10. 4.
 */
@Getter
@Setter
public class Company extends ValueObjectBase {

    private static final long serialVersionUID = 4442244029750273886L;

    private String code;
    private String name;
    private String description;
    private long amount;

    private final List<User> users = Lists.newLinkedList();


    @Override
    public int hashCode() {
        return HashTool.compute(code, name);
    }

    @Override
    protected Objects.ToStringHelper buildStringHelper() {
        return super.buildStringHelper()
                .add("code", code)
                .add("name", name)
                .add("amount", amount);
    }
}
