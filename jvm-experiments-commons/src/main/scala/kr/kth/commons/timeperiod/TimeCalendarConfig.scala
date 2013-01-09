package kr.kth.commons.timeperiod

import beans.BeanProperty
import java.util.Locale
import kr.kth.commons.base.ValueObjectBase
import kr.kth.commons.tools.ScalaHash
import org.joda.time.Duration
import tools.WeekTools

/**
 * TimeCalendar의 설정 정보를 표현한다.
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 12. 29.
 */
@SerialVersionUID(939047587024793248L)
class TimeCalendarConfig(aLocale: Locale = Locale.getDefault,
                         aWeekOfYearRule: WeekOfYearRuleKind = WeekOfYearRuleKind.Iso8601)
    extends ValueObjectBase {

    @BeanProperty var locale: Locale = aLocale
    @BeanProperty var yearKind: YearKind = YearKind.CalendarYear
    @BeanProperty var startOffset: Duration = Duration.ZERO
    @BeanProperty var endOffset: Duration = Duration.millis(TimeSpec.MinPositiveDuration)
    @BeanProperty var yearBaseMonth: Int = TimeSpec.CalendarYearStartMonth
    @BeanProperty var weekOfYearRule: WeekOfYearRuleKind = aWeekOfYearRule

    def getCalendarWeekRule = WeekTools.getCalendarWeekRule(locale, weekOfYearRule)

    def getFirstDayOfWeek: DayOfWeek = WeekTools.getFirstDayOfWeek(locale, weekOfYearRule)

    override def hashCode() = ScalaHash.compute(locale, weekOfYearRule, startOffset, endOffset)

    protected override def buildStringHelper() =
        super.buildStringHelper()
            .add("locale", locale)
            .add("weekOfYearRule", weekOfYearRule)
            .add("startOffset", startOffset)
            .add("endOffset", endOffset)
}

object TimeCalendarConfig {

    lazy val Default: TimeCalendarConfig = new TimeCalendarConfig()

    def apply(): TimeCalendarConfig = new TimeCalendarConfig()

    def apply(locale: Locale, weekOfYearRule: WeekOfYearRuleKind = WeekOfYearRuleKind.Iso8601) = {
        val loc = if (locale != null) locale else Locale.getDefault
        new TimeCalendarConfig(loc, weekOfYearRule)
    }
}