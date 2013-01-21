package kr.kth.commons.tools

import kr.kth.commons.slf4j.Logging
import org.junit.{Assert, Test}
import scala.annotation.varargs
import scala.reflect.{ClassTag, classTag}


/**
 * kr.kth.commons.tools.ScalaReflectsTest
 * User: sunghyouk.bae@gmail.com
 * Date: 13. 1. 18
 */
class ScalaReflectsTest extends Logging {

    class Foo[T: ClassTag]() {
        def create: T = classTag[T].runtimeClass.newInstance().asInstanceOf[T]

        @varargs
        def create(initArgs: Any*): T = {
            log.debug("Create [{}]", classTag[T].runtimeClass.getName)
            val parameterTypes =
                initArgs.map(ScalaReflects.asJavaClass(_)).toArray
            val constructor = classTag[T].runtimeClass.getConstructor(parameterTypes: _*)
            constructor.newInstance(initArgs.map(_.asInstanceOf[AnyRef]): _*).asInstanceOf[T]
        }

        @varargs
        def createWithTypes(initArgs: Any*)(initArgsTypes: Class[_]*): T = {
            val parameterTypes =
                if (initArgsTypes != null) initArgsTypes.toArray
                else initArgs.map(ScalaReflects.asJavaClass(_)).toArray

            log.debug("Create [{}]", classTag[T].runtimeClass.getName)
            val constructor = classTag[T].runtimeClass.getConstructor(parameterTypes: _*)
            constructor.newInstance(initArgs.map(_.asInstanceOf[AnyRef]): _*).asInstanceOf[T]
        }
    }

    @Test
    def genericNewInstanceTest() {
        val foo = new Foo[MyClass]()
        val myClass = foo.create
        Assert.assertNotNull(myClass)
        Assert.assertTrue(myClass.isInstanceOf[MyClass])
    }

    @Test
    def genericNewInstanceWithParameter() {
        val foo = new Foo[MyClass]()
        val myClass = foo.create(1, "dynamic")

        Assert.assertNotNull(myClass)
        Assert.assertTrue(myClass.isInstanceOf[MyClass])
    }

    @Test
    def instancingByDefaultContructor() {
        val instance = ScalaReflects.newInstance[MyClass]()
        Assert.assertNotNull(instance)
        Assert.assertTrue(instance.isInstanceOf[MyClass])
    }

    @Test
    def instancingByParameterizedContructor() {
        val instance = ScalaReflects.newInstance[MyClass](100, "Dynamic")
        Assert.assertNotNull(instance)
        Assert.assertTrue(instance.isInstanceOf[MyClass])
    }

    @Test
    def instancingByParameterizedContructorWithTypes() {
        val instance = ScalaReflects.newInstanceWithTypes[MyClass](100, "Dynamic")(classOf[Int], classOf[String])
        Assert.assertNotNull(instance)
        Assert.assertTrue(instance.isInstanceOf[MyClass])
    }
}

class MyClass(var id: Int, var name: String) {
    def this() {
        this(0, "Unknown")
    }
}