package kr.kth.commons.tools

import annotation.varargs

/**
 * Scala 용 Hash 생성 툴
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 12. 29.
 */
object ScalaHash {

    def instance = this

    /**
     * 지정된 객체들의 Hash Code를 조합한 Hash Code를 생성합니다.
     */
    @inline
    @varargs
    def compute(objs: Any*): Int = {
        HashTool.compute(objs.map(_.asInstanceOf[AnyRef]): _*)
    }
}
