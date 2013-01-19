package kr.kth.commons.tools


/**
 * Scala 용 Hash 생성 툴
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 12. 29.
 */
object ScalaHash {

    val NullValue: Int = 0
    val Factor: Int = 31

    @inline private def computeInternal(x: => Any): Int = {
        if (x != null) x.hashCode() else NullValue
    }

    /**
     * 지정된 객체들의 Hash Code를 조합한 Hash Code를 생성합니다.
     */
    @inline def compute(objs: Any*): Int = {
        var hash = NullValue
        objs.map(x => hash = hash * Factor + computeInternal(x))

        hash
        //HashTool.compute(objs.map(_.asInstanceOf[AnyRef]): _*)
    }
}
