package kr.nsoft.data.hibernate.usertype;

import kr.nsoft.commons.Guard;
import kr.nsoft.data.domain.model.DateTimeRange;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.type.DateType;
import org.hibernate.type.Type;
import org.hibernate.usertype.CompositeUserType;
import org.joda.time.DateTime;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.Objects;

/**
 * kr.nsoft.data.hibernate.usertype.TimeRangeUserType
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 12. 3.
 */
@Slf4j
public class TimeRangeUserType implements CompositeUserType {

    public static DateTimeRange asDateTimeRange(Object value) {
        if (value == null)
            return null;

        if (value instanceof DateTimeRange)
            return (DateTimeRange) value;
        return null;
    }

    @Override
    public String[] getPropertyNames() {
        return new String[]{"getStart", "getEnd"};
    }

    @Override
    public Type[] getPropertyTypes() {
        return new Type[]{DateType.INSTANCE, DateType.INSTANCE};
    }

    @Override
    public Object getPropertyValue(Object component, int property) throws HibernateException {
        if (component == null)
            return null;

        DateTimeRange dateTimeRange = asDateTimeRange(component);

        switch (property) {
            case 0:
                return dateTimeRange.getStart();
            case 1:
                return dateTimeRange.getEnd();
            default:
                throw new IllegalArgumentException("복합 수형의 인덱스 범위가 벗어났습니다. 0, 1만 가능합니다. property=" + property);
        }
    }

    @Override
    public void setPropertyValue(Object component, int property, Object value) throws HibernateException {
        Guard.shouldNotBeNull(component, "component");

        DateTimeRange timeRange = asDateTimeRange(component);

        switch (property) {
            case 0:
                timeRange.setStart((value != null) ? new DateTime(value) : DateTimeRange.MinPeriodTime);
                break;
            case 1:
                timeRange.setEnd((value != null) ? new DateTime(value) : DateTimeRange.MaxPeriodTime);
                break;
            default:
                throw new IllegalArgumentException("복합 수형의 인덱스 범위가 벗어났습니다. 0, 1만 가능합니다. property=" + property);
        }
    }

    @Override
    public Class returnedClass() {
        return DateTimeRange.class;
    }

    @Override
    public boolean equals(Object x, Object y) throws HibernateException {
        return Objects.equals(x, y);
    }

    @Override
    public int hashCode(Object x) throws HibernateException {
        return Objects.hashCode(x);
    }

    @Override
    public Object nullSafeGet(ResultSet rs, String[] names, SessionImplementor session, Object owner)
            throws HibernateException, SQLException {

        Date start = (Date) DateType.INSTANCE.nullSafeGet(rs, names[0], session, owner);
        Date end = (Date) DateType.INSTANCE.nullSafeGet(rs, names[1], session, owner);

        return new DateTimeRange(new DateTime(start), new DateTime(end));
    }

    @Override
    public void nullSafeSet(PreparedStatement st, Object value, int index, SessionImplementor session)
            throws HibernateException, SQLException {

        if (value == null) {
            DateType.INSTANCE.nullSafeSet(st, null, index, session);
            DateType.INSTANCE.nullSafeSet(st, null, index + 1, session);
        } else {
            DateTimeRange range = asDateTimeRange(value);
            DateType.INSTANCE.nullSafeSet(st, range.getStart(), index, session);
            DateType.INSTANCE.nullSafeSet(st, range.getEnd(), index + 1, session);
        }
    }

    @Override
    public Object deepCopy(Object value) throws HibernateException {
        if (value == null)
            return null;

        DateTimeRange range = asDateTimeRange(value);
        return new DateTimeRange(range);
    }

    @Override
    public boolean isMutable() {
        return true;
    }

    @Override
    public Serializable disassemble(Object value, SessionImplementor session) throws HibernateException {
        return (Serializable) deepCopy(value);
    }

    @Override
    public Object assemble(Serializable cached, SessionImplementor session, Object owner) throws HibernateException {
        return deepCopy(cached);
    }

    @Override
    public Object replace(Object original, Object target, SessionImplementor session, Object owner) throws HibernateException {
        return deepCopy(original);
    }
}
