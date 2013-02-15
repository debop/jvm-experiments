package examples.web;

import kr.nsoft.commons.ValueObjectBase;
import lombok.Getter;
import lombok.Setter;

/**
 * examples.web.Employee
 * User: sunghyouk.bae@gmail.com
 * Date: 13. 2. 15
 */
@Getter
@Setter
public class Employee extends ValueObjectBase {

    private String name;
    private int empId = -1;

    public Employee() {
        this("", -1);
    }

    public Employee(String name, int id) {
        this.name = name;
        this.empId = id;
    }
}
