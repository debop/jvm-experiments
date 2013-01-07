package kr.kth.commons.slf4j

import org.slf4j.{Logger => Slf4jLogger, Marker}
import annotation.varargs

/**
 * Scala를 위한 Logger 입니다.
 * User: sunghyouk.bae@gmail.com
 * Date: 13. 1. 6.
 */
class Logger(val logger: Slf4jLogger) {

  import scala.language.implicitConversions

  @inline final def name = logger.getName

  @inline
  @varargs final def toJavaArgs(args: Any*) = args.map(_.asInstanceOf[AnyRef])

  @inline final def isTraceEnabled = logger.isTraceEnabled

  @inline final def isTraceEnabled(marker: Marker) = logger.isTraceEnabled(marker)

  @inline final def trace(msg: String) {
    if (isTraceEnabled)
      logger.trace(msg)
  }

  @inline final def trace(marker: Marker, msg: String) {
    if (isTraceEnabled(marker))
      logger.trace(marker, msg)
  }

  @inline final def trace(msg: String, t: Throwable) {
    if (isTraceEnabled)
      logger.trace(msg, t)
  }

  @inline final def trace(marker: Marker, msg: String, t: Throwable) {
    if (isTraceEnabled(marker))
      logger.trace(marker, msg, t)
  }

  @inline
  @varargs final def trace(format: String, arguments: Any*) {
    if (isTraceEnabled)
      logger.trace(format, toJavaArgs(arguments): _*)
  }

  @inline
  @varargs final def trace(marker: Marker, format: String, arguments: Any*) {
    if (isTraceEnabled(marker))
      logger.trace(marker, format, toJavaArgs(arguments): _*)
  }

  @inline final def isDebugEnabled = logger.isDebugEnabled

  @inline final def isDebugEnabled(marker: Marker) = logger.isDebugEnabled(marker)

  @inline final def debug(msg: String) =
    if (isDebugEnabled)
      logger.debug(msg)

  @inline final def debug(marker: Marker, msg: String) =
    if (isDebugEnabled(marker))
      logger.debug(marker, msg)


  @inline final def debug(msg: String, t: Throwable) =
    if (isDebugEnabled)
      logger.debug(msg, t)

  @inline final def debug(marker: Marker, msg: String, t: Throwable) =
    if (isDebugEnabled(marker))
      logger.debug(marker, msg, t)


  @inline
  @varargs final def debug(format: String, arguments: Any*) =
    if (isDebugEnabled)
      logger.debug(format, toJavaArgs(arguments): _*)

  @inline
  @varargs final def debug(marker: Marker, format: String, arguments: Any*): Unit =
    if (isDebugEnabled(marker))
      logger.debug(marker, format, toJavaArgs(arguments): _*)


  @inline final def isInfoEnabled = logger.isInfoEnabled

  @inline final def isInfoEnabled(marker: Marker) = logger.isInfoEnabled(marker)

  @inline final def info(msg: String) =
    if (isInfoEnabled)
      logger.info(msg)

  @inline final def info(marker: Marker, msg: String) =
    if (isInfoEnabled(marker))
      logger.trace(marker, msg)


  @inline final def info(msg: String, t: Throwable) =
    if (isInfoEnabled)
      logger.info(msg, t)

  @inline final def info(marker: Marker, msg: String, t: Throwable) =
    if (isInfoEnabled(marker))
      logger.info(marker, msg, t)

  @inline
  @varargs final def info(format: String, arguments: Any*) =
    if (isInfoEnabled)
      logger.info(format, toJavaArgs(arguments): _*)

  @inline
  @varargs final def info(marker: Marker, format: String, arguments: Any*): Unit =
    if (isInfoEnabled(marker))
      logger.info(marker, format, toJavaArgs(arguments): _*)


  @inline final def isWarnEnabled = logger.isWarnEnabled

  @inline final def isWarnEnabled(marker: Marker) = logger.isWarnEnabled(marker)

  @inline final def warn(msg: String) {
    if (isWarnEnabled)
      logger.warn(msg)
  }

  @inline final def warn(marker: Marker, msg: String) {
    if (isWarnEnabled(marker))
      logger.warn(marker, msg)
  }

  @inline final def warn(msg: String, t: Throwable) {
    if (isWarnEnabled)
      logger.warn(msg, t)
  }

  @inline final def warn(marker: Marker, msg: String, t: Throwable) {
    if (isWarnEnabled(marker))
      logger.warn(marker, msg, t)
  }

  @inline
  @varargs final def warn(format: String, arguments: Any*) {
    if (isWarnEnabled)
      logger.warn(format, toJavaArgs(arguments): _*)
  }

  @inline
  @varargs final def warn(marker: Marker, format: String, arguments: Any*) {
    if (isWarnEnabled(marker))
      logger.warn(marker, format, toJavaArgs(arguments): _*)
  }


  @inline final def isErrorEnabled = logger.isErrorEnabled

  @inline final def isErrorEnabled(marker: Marker) = logger.isErrorEnabled(marker)

  @inline final def error(msg: String) {
    if (isErrorEnabled)
      logger.error(msg)
  }

  @inline final def error(marker: Marker, msg: String) {
    if (isErrorEnabled(marker))
      logger.error(marker, msg)
  }

  @inline final def error(msg: String, t: Throwable) {
    if (isErrorEnabled)
      logger.error(msg, t)
  }

  @inline final def error(marker: Marker, msg: String, t: Throwable) {
    if (isErrorEnabled(marker))
      logger.error(marker, msg, t)
  }

  @inline
  @varargs final def error(format: String, arguments: Any*) {
    if (isErrorEnabled)
      logger.error(format, toJavaArgs(arguments): _*)
  }

  @inline
  @varargs final def error(marker: Marker, format: String, arguments: Any*) {
    if (isErrorEnabled(marker))
      logger.error(marker, format, toJavaArgs(arguments): _*)
  }
}

object Logger {

  import scala.reflect.{classTag, ClassTag}

  lazy val RootLoggerName = Slf4jLogger.ROOT_LOGGER_NAME

  /**
   * 지정된 이름을 Logger 이름으로 사용합니다. 예: Logger("LoggerName")
   * @param name
   * @return
   */
  def apply(name: String): Logger = new Logger(org.slf4j.LoggerFactory.getLogger(name))

  /**
   * 지정한 클래스 수형에 맞는 Logger를 반환합니다. 예: Logger(this.type)
   * @param cls
   * @return
   */
  def apply(cls: Class[_]): Logger = apply(cls.getName)

  /**
   * 특정 클래스에 맞는 Logger 를 반환합니다. 예: Logger[classOf[MyClass]]
   * @tparam C
   * @return
   */
  def apply[C: ClassTag](): Logger = apply(classTag[C].runtimeClass.getName)

  /**
   * Root Logger
   * @return
   */
  def rootLogger = apply(RootLoggerName)
}
