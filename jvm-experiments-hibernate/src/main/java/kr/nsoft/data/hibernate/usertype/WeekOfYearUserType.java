package kr.nsoft.data.hibernate.usertype;

import com.google.common.base.Objects;
import kr.nsoft.commons.Guard;
import kr.nsoft.commons.YearWeek;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.type.IntegerType;
import org.hibernate.type.Type;
import org.hibernate.usertype.CompositeUserType;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 주차 (Week of Year)를 표현하는 클래스 {@link YearWeek}를
 * 하나의 속성값으로 처리하기 위한 {@link org.hibernate.usertype.CompositeUserType} 입니다.
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 9. 18
 */
@Slf4j
public class WeekOfYearUserType implements CompositeUserType {

    /**
     * 객체를 {@link kr.nsoft.commons.YearWeek} 로 변환합니다.
     */
    private static YearWeek asYearWeek(Object value) {
        if (value == null)
            return YearWeek.MIN_VALUE;

        return (YearWeek) value;
    }

    @Override
    public String[] getPropertyNames() {
        return new String[]{"year", "week"};
    }

    @Override
    public Type[] getPropertyTypes() {
        return new Type[]{IntegerType.INSTANCE, IntegerType.INSTANCE};
    }

    @Override
    public Object getPropertyValue(Object component, int property) throws HibernateException {
        Guard.shouldNotBeNull(component, "component");
        YearWeek yw = asYearWeek(component);

        if (property == 0)
            return yw.getYear();
        else if (property == 1)
            return yw.getWeek();
        else
            throw new HibernateException("property index가 범위를 벗어났습니다. 0, 1 중에 하나여야 합니다. property=" + property);
    }

    @Override
    public void setPropertyValue(Object component, int property, Object value) throws HibernateException {
        Guard.shouldNotBeNull(component, "component");
        YearWeek yw = asYearWeek(component);

        if (property == 0)
            yw.setYear((Integer) Objects.firstNonNull(value, 0));
        else if (property == 1)
            yw.setWeek((Integer) Objects.firstNonNull(value, 0));
        else
            throw new HibernateException("property index가 범위를 벗어났습니다. 0, 1 중에 하나여야 합니다. property=" + property);
    }

    @Override
    public Class returnedClass() {
        return YearWeek.class;
    }

    @Override
    public boolean equals(Object x, Object y) throws HibernateException {
        return Objects.equal(x, y);
    }

    @Override
    public int hashCode(Object x) throws HibernateException {
        return Objects.hashCode(x);
    }

    @Override
    public Object nullSafeGet(ResultSet rs,
                              String[] names,
                              SessionImplementor session,
                              Object owner) throws HibernateException,
            SQLException {
        Integer year = IntegerType.INSTANCE.nullSafeGet(rs, names[0], session);
        Integer week = IntegerType.INSTANCE.nullSafeGet(rs, names[1], session);
        return new YearWeek(year, week);
    }

    @Override
    public void nullSafeSet(PreparedStatement st,
                            Object value,
                            int index,
                            SessionImplementor session) throws HibernateException,
            SQLException {
        if (value == null) {
            IntegerType.INSTANCE.nullSafeSet(st, null, index, session);
            IntegerType.INSTANCE.nullSafeSet(st, null, index + 1, session);
        } else {
            YearWeek yw = asYearWeek(value);
            IntegerType.INSTANCE.nullSafeSet(st, yw.getYear(), index, session);
            IntegerType.INSTANCE.nullSafeSet(st, yw.getWeek(), index + 1, session);
        }
    }

    @Override
    public Object deepCopy(Object value) throws HibernateException {
        if (value == null)
            return value;

        return new YearWeek(asYearWeek(value));
    }

    @Override
    public boolean isMutable() {
        return true;
    }

    @Override
    public Serializable disassemble(Object value,
                                    SessionImplementor session) throws HibernateException {
        return (YearWeek) deepCopy(value);
    }

    @Override
    public Object assemble(Serializable cached,
                           SessionImplementor session,
                           Object owner) throws HibernateException {
        return (YearWeek) deepCopy(cached);
    }

    @Override
    public Object replace(Object original,
                          Object target,
                          SessionImplementor session,
                          Object owner) throws HibernateException {
        return (YearWeek) deepCopy(original);
    }
}
