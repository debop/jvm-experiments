package kr.kth.commons.tools

import scala.reflect.{ClassTag, classTag}


/**
 * kr.kth.timeperiod.ScalaReflects
 * User: sunghyouk.bae@gmail.com
 * Date: 13. 1. 18
 */
object ScalaReflects {

    /**
    * Generic 수형의 클래스에 대해 인스턴스를 생성합니다.
    * @since
    */
    def newInstance[T: ClassTag]():T = {
        classTag[T].runtimeClass.newInstance().asInstanceOf[T]
    }
}
