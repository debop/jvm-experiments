package kr.kth.commons

import org.joda.time.DateTime

/**
 * kr.kth.commons.base package object
 * User: sunghyouk.bae@gmail.com
 * Date: 13. 1. 9.
 */
package object base {

    /**
     * 현재 날짜
     * @return
     */
    def today = new DateTime().withTimeAtStartOfDay()
}
