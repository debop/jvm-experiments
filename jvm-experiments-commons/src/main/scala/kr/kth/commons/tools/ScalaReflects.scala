package kr.kth.commons.tools

import scala.reflect.{ClassTag, classTag}


/**
 * kr.kth.timeperiod.ScalaReflects
 * User: sunghyouk.bae@gmail.com
 * Date: 13. 1. 18
 */
object ScalaReflects {

    /**
     * Java의 Primitive 수형에 대한 Box된 Scala 클래스를 Unbox한 Java Primitive 수형 타입을 구합니다.
     * @param x 스칼라 수형
     * @return  Java의 Primitive Type
     */
    @inline
    def asJavaClass(x: Any): Class[_] = x match {

        case x: scala.Boolean => java.lang.Boolean.TYPE
        case x: scala.Char => java.lang.Character.TYPE
        case x: scala.Byte => java.lang.Byte.TYPE
        case x: scala.Short => java.lang.Short.TYPE
        case x: scala.Int => java.lang.Integer.TYPE
        case x: scala.Long => java.lang.Long.TYPE
        case x: scala.Float => java.lang.Float.TYPE
        case x: scala.Double => java.lang.Double.TYPE
        case _ => x.getClass
    }

    /**
     * Generic 수형의 클래스에 대해 기본 생성자를 통한 인스턴스를 생성합니다.
     */
    def newInstance[T: ClassTag](): T = {
        classTag[T].runtimeClass.newInstance().asInstanceOf[T]
    }

    /**
     * Generic 수형의 클래스에 대해 지정된 생성자 인자에 해당하는 생성자를 통해 인스턴스를 생성합니다.
     */
    def newInstance[T: ClassTag](initArgs: Any*): T = {
        if (initArgs == null || initArgs.length == 0)
            return newInstance[T]()

        val parameterTypes = initArgs.map(asJavaClass(_)).toArray
        val constructor = classTag[T].runtimeClass.getConstructor(parameterTypes: _*)
        constructor.newInstance(initArgs.map(_.asInstanceOf[AnyRef]): _*).asInstanceOf[T]
    }

    def newInstanceWithTypes[T: ClassTag](initArgs: Any*)(initArgsTypes: Class[_]*): T = {
        if (initArgs == null || initArgs.length == 0)
            return newInstance[T]()

        val parameterTypes =
            if (initArgsTypes != null) initArgsTypes.toArray
            else initArgs.map(asJavaClass(_)).toArray

        val constructor = classTag[T].runtimeClass.getConstructor(parameterTypes: _*)
        constructor.newInstance(initArgs.map(_.asInstanceOf[AnyRef]): _*).asInstanceOf[T]
    }
}
