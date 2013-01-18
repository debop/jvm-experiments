package kr.kth.commons.tools

import kr.kth.commons.slf4j.Logging
import org.joda.time.DateTime
import org.junit.{Assert, Test}
import scala.reflect.{ClassTag, classTag}
import kr.kth.commons.ISerializer
import kr.kth.commons.json.GsonSerializer

/**
 * kr.kth.commons.tools.ScalaReflectsTest
 * User: sunghyouk.bae@gmail.com
 * Date: 13. 1. 18
 */
class ScalaReflectsTest extends Logging  {

    class Foo[T <: ISerializer : ClassTag](val startDate:DateTime) {
        def create:T = classTag[T].runtimeClass.newInstance().asInstanceOf[T]
    }

    @Test
    def genericNewInstanceTest() {
        val foo = new Foo[GsonSerializer](new DateTime())
        val serializer = foo.create
        Assert.assertNotNull(serializer)
        Assert.assertTrue(serializer.isInstanceOf[GsonSerializer])
    }

    @Test
    def instancingByDefaultContructor() {
        val instance = ScalaReflects.newInstance[GsonSerializer]
        Assert.assertNotNull(instance)
        Assert.assertTrue(instance.isInstanceOf[GsonSerializer])
    }
}
