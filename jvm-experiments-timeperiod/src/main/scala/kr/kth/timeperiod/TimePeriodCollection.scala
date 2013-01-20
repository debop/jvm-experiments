package kr.kth.timeperiod

import kr.kth.commons.slf4j.Logging
import scala.collection.TraversableOnce

/**
 * kr.kth.timeperiod.TimePeriodCollection
 * User: sunghyouk.bae@gmail.com
 * Date: 13. 1. 15
 */
class TimePeriodCollection extends TimePeriodContainer with ITimePeriodCollection {
}

object TimePeriodCollection extends Logging {

    def apply() = new TimePeriodCollection()

    def apply(elems: ITimePeriod*): TimePeriodCollection = {
        val collection = new TimePeriodCollection()
        collection.appendAll(elems)
        collection
    }

    def apply(items: TraversableOnce[ITimePeriod]): TimePeriodCollection = {
        val collection = new TimePeriodCollection()
        collection.appendAll(items)
        collection
    }
}
