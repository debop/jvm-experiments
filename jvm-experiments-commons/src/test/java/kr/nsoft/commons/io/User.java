package kr.nsoft.commons.io;

import com.google.common.base.Objects;
import kr.nsoft.commons.ValueObjectBase;
import kr.nsoft.commons.tools.HashTool;
import lombok.Getter;
import lombok.Setter;


/**
 * pudding.pudding.commons.core.io.User
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 10. 4.
 */
@Getter
@Setter
public class User extends ValueObjectBase {

    private static final long serialVersionUID = -1375942267796202939L;

    private String name;
    private String employeeNumber;
    private String address;

    @Override
    public int hashCode() {
        return HashTool.compute(name, employeeNumber);
    }

    @Override
    protected Objects.ToStringHelper buildStringHelper() {
        return super.buildStringHelper()
                .add("name", name)
                .add("employeeNumber", employeeNumber)
                .add("address", address);
    }
}
