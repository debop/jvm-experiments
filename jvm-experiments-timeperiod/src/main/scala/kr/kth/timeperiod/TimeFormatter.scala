package kr.kth.timeperiod

import IntervalEdge._, DurationFormatKind._
import java.util.Locale
import kr.kth.commons.slf4j.Logging
import org.joda.time.DateTime

/**
 * 시간 정보를 문자열로 포맷팅하는 기본 Time Formatter 입니다.
 * User: sunghyouk.bae@gmail.com
 * Date: 13. 1. 13.
 */
class TimeFormatter extends ITimeFormatter with Logging {

	def getListSeparator: String = ???

	def getContextSeparator: String = ???

	def getStartEndSeparator: String = ???

	def getDurationSeparator: String = ???

	def getDateFormat: String = ???

	def getShortDateFormat: String = ???

	def getLongDateFormat: String = ???

	def getShortTimeFormat: String = ???

	def getLongTimeFormat: String = ???

	def getDurationKind: DurationFormatKind.DurationFormatKind = ???

	def isUseDurationSeconds: Boolean = ???

	def getCollection(count: Int): String = ???

	def getCollectionPeriod(count: Int, start: DateTime, end: DateTime, duration: Long): String = ???

	def getDate(date: DateTime): String = ???

	def getShortDate(date: DateTime): String = ???

	def getLongDate(date: DateTime): String = ???

	def getTime(date: DateTime): String = ???

	def getShortTime(date: DateTime): String = ???

	def getLongTime(date: DateTime): String = ???

	def getDuration(duration: Long, durationFormatKind: DurationFormatKind.DurationFormatKind): String = ???

	def getPeriod(start: DateTime, end: DateTime, duration: Long): String = ???

	def getInterval(start: DateTime, end: DateTime, startEdge: IntervalEdge.IntervalEdge, endEdge: IntervalEdge.IntervalEdge, duration: Long): String = ???

	def getCalendarPeriod(start: String, end: String, duration: Long, startContex: String, endContext: String): String = ???
}

/**
 * 시간 정보를 문자열로 포맷팅하는 Trait 입니다.
 * User: sunghyouk.bae@gmail.com
 * Date: 13. 1. 13.
 */
trait ITimeFormatter {

	def getLocale: Locale = Locale.getDefault

	def getListSeparator: String

	def getContextSeparator: String

	def getStartEndSeparator: String

	def getDurationSeparator: String

	def getDateFormat: String

	def getShortDateFormat: String

	def getLongDateFormat: String

	def getShortTimeFormat: String

	def getLongTimeFormat: String

	def getDurationKind: DurationFormatKind

	def isUseDurationSeconds: Boolean

	def getCollection(count: Int): String

	def getCollectionPeriod(count: Int, start: DateTime, end: DateTime, duration: Long): String

	def getDate(date: DateTime): String

	def getShortDate(date: DateTime): String

	def getLongDate(date: DateTime): String

	def getTime(date: DateTime): String

	def getShortTime(date: DateTime): String

	def getLongTime(date: DateTime): String

	def getDuration(duration: Long, durationFormatKind: DurationFormatKind = DurationFormatKind.Compact): String

	def getPeriod(start: DateTime, end: DateTime, duration: Long): String

	def getInterval(start: DateTime, end: DateTime, startEdge: IntervalEdge, endEdge: IntervalEdge, duration: Long): String

	def getCalendarPeriod(start: String, end: String, startContex: String, endContext: String, duration: Long): String
}

object TimeFormatter extends Logging {

	lazy val instance = new TimeFormatter()
}


