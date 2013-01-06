package kr.kth.commons.slf4j

import org.slf4j.Marker
import annotation.varargs

/**
 * 로그를 사용할 클래스에서 MixIn 방식으로 상속해서 사용하면 됩니다. ( class A extends Logger ... )
 * User: sunghyouk.bae@gmail.com
 * Date: 13. 1. 6.
 */
trait Logging {

	private lazy val _logger = Logger(getClass)

	protected def log: Logger = _logger

	protected def loggerName = log.name

	protected def isTranceEnabled = log.isTraceEnabled

	protected def trace(msg: String) = log.trace(msg)

	protected def trace(marker: Marker, msg: String) = log.trace(marker, msg)

	protected def trace(msg: String, t: Throwable) = log.trace(msg, t)

	protected def trace(marker: Marker, msg: String, t: Throwable) = log.trace(marker, msg, t)

	protected def trace(format: String, @varargs arguments: Any*) = log.trace(format, arguments)

	protected def trace(marker: Marker, format: String, @varargs arguments: Any*) = log.trace(marker, format, arguments)


	protected def isDebugEnabled = log.isDebugEnabled

	protected def debug(msg: String) = log.debug(msg)

	protected def debug(marker: Marker, msg: String) = log.debug(marker, msg)

	protected def debug(msg: String, t: Throwable) = log.debug(msg, t)

	protected def debug(marker: Marker, msg: String, t: Throwable) = log.debug(marker, msg, t)

	protected def debug(format: String, @varargs arguments: Any*) = log.debug(format, arguments)

	protected def debug(marker: Marker, format: String, @varargs arguments: Any*) = log.debug(marker, format, arguments)


	protected def isInfoEnabled = log.isInfoEnabled

	protected def info(msg: String) = log.info(msg)

	protected def info(marker: Marker, msg: String) = log.info(marker, msg)

	protected def info(msg: String, t: Throwable) = log.info(msg, t)

	protected def info(marker: Marker, msg: String, t: Throwable) = log.info(marker, msg, t)

	protected def info(format: String, @varargs arguments: Any*) = log.info(format, arguments)

	protected def info(marker: Marker, format: String, @varargs arguments: Any*) = log.info(marker, format, arguments)


	protected def isWarnEnabled = log.isWarnEnabled

	protected def warn(msg: String) = log.warn(msg)

	protected def warn(marker: Marker, msg: String) = log.warn(marker, msg)

	protected def warn(msg: String, t: Throwable) = log.warn(msg, t)

	protected def warn(marker: Marker, msg: String, t: Throwable) = log.warn(marker, msg, t)

	protected def warn(format: String, @varargs arguments: Any*) = log.warn(format, arguments)

	protected def warn(marker: Marker, format: String, @varargs arguments: Any*) = log.warn(marker, format, arguments)


	protected def isErrorEnabled = log.isErrorEnabled

	protected def error(msg: String) = log.error(msg)

	protected def error(marker: Marker, msg: String) = log.error(marker, msg)

	protected def error(msg: String, t: Throwable) = log.error(msg, t)

	protected def error(marker: Marker, msg: String, t: Throwable) = log.error(marker, msg, t)

	protected def error(format: String, @varargs arguments: Any*) = log.error(format, arguments)

	protected def error(marker: Marker, format: String, @varargs arguments: Any*) = log.error(marker, format, arguments)

}
