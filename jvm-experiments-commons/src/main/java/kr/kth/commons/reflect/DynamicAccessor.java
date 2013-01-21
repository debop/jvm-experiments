package kr.kth.commons.reflect;

import kr.kth.commons.Guard;
import lombok.extern.slf4j.Slf4j;

/**
 * 동적으로 객체의 속성, 메소드에 접근할 수 있는 접근자입니다.
 * User: sunghyouk.bae@gmail.com
 * Date: 13. 1. 21
 */
@Slf4j
public class DynamicAccessor<T> {

    private final Class<T> targetType;
    private final ConstructorAccess<T> ctorAccessor;
    private final FieldAccess fieldAccessor;
    private final MethodAccess methodAccessor;

    public DynamicAccessor(Class<T> targetType) {
        Guard.shouldNotBeNull(targetType, "targetType");
        if (log.isDebugEnabled())
            log.debug("");
        this.targetType = targetType;
        this.ctorAccessor = ConstructorAccess.get(this.targetType);
        this.fieldAccessor = FieldAccess.get(this.targetType);
        this.methodAccessor = MethodAccess.get(this.targetType);
    }

    public Object getField(Object instance, String fieldName) {
        return fieldAccessor.get(instance, fieldName);
    }

    public void setField(Object instance, String fieldName, Object nv) {
        fieldAccessor.set(instance, fieldName, nv);
    }

    public void setFieldBoolean(Object instance, String fieldName, boolean nv) {
        fieldAccessor.setBoolean(instance, fieldAccessor.getIndex(fieldName), nv);
    }

    public void setFieldByte(Object instance, String fieldName, byte nv) {
        fieldAccessor.setByte(instance, fieldAccessor.getIndex(fieldName), nv);
    }

    public void setFieldChar(Object instance, String fieldName, char nv) {
        fieldAccessor.setChar(instance, fieldAccessor.getIndex(fieldName), nv);
    }

    public void setFieldDouble(Object instance, String fieldName, double nv) {
        fieldAccessor.setDouble(instance, fieldAccessor.getIndex(fieldName), nv);
    }

    public void setFieldFloat(Object instance, String fieldName, float nv) {
        fieldAccessor.setFloat(instance, fieldAccessor.getIndex(fieldName), nv);
    }

    public void setFieldInt(Object instance, String fieldName, int nv) {
        fieldAccessor.setInt(instance, fieldAccessor.getIndex(fieldName), nv);
    }

    public void setFieldLong(Object instance, String fieldName, long nv) {
        fieldAccessor.setLong(instance, fieldAccessor.getIndex(fieldName), nv);
    }

    public void setFieldShort(Object instance, String fieldName, short nv) {
        fieldAccessor.setShort(instance, fieldAccessor.getIndex(fieldName), nv);
    }

    public Object invoke(Object instance, String methodName, Object... args) {
        return methodAccessor.invoke(instance, methodName, args);
    }
}
