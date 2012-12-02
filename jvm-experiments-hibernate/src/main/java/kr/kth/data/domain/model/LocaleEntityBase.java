package kr.kth.data.domain.model;

import kr.kth.commons.tools.MapperTool;
import kr.kth.commons.tools.ReflectTool;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

/**
 * 지역화정보를 가지는 엔티티의 추상화 클래스
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 9. 19
 */
@Slf4j
public abstract class LocaleEntityBase<TId extends Serializable, TLocaleValue extends LocaleValue>
	extends EntityBase<TId> implements LocaleEntity<TLocaleValue> {

	private static final long serialVersionUID = 8316501523660904445L;

	@Getter(lazy = true)
	private final Map<Locale, TLocaleValue> localeMap = new HashMap<>();

	private TLocaleValue defaultLocaleValue = null;

	/**
	 * 기본 LOcale 정보를 생성하여 제공합니다.
	 */
	public final TLocaleValue getDefaultLocale() {
		if (defaultLocaleValue == null) {
			try {
				defaultLocaleValue = createDefaultLocaleValue();
			} catch (Exception ex) {
				log.error("기본 LocaleValue를 얻는데 실패했습니다.", ex);
			}
		}
		return defaultLocaleValue;
	}

	protected TLocaleValue createDefaultLocaleValue() {
		try {
			Class<TLocaleValue> localeValueClass = ReflectTool.getGenericParameterType(this, 1);

			if (log.isDebugEnabled())
				log.debug("기본 Locale 정보가 없습니다. 엔티티 [{}] 속성으로 기본 Locale [{}] 정보를 생성합니다...",
				          getClass().getName(), localeValueClass.getName());

			// ModelMapper가 엔티티의 속성 중 LocaleValue 속성과 같은 것들에 대해 값을 복사한다.
			//
			return MapperTool.map(this, localeValueClass);

		} catch (Exception ex) {
			throw new IllegalStateException("기본 Locale 정보를 생성하는데 실패했습니다.", ex);
		}
	}

	@Override
	public TLocaleValue getLocaleValue(Locale locale) {
		return getLocaleMap().get(locale);
	}

	@Override
	public Set<Locale> getLcoales() {
		return getLocaleMap().keySet();
	}

	@Override
	public void addLocaleValue(Locale locale, TLocaleValue localeValue) {
		getLocaleMap().put(locale, localeValue);
	}

	@Override
	public void removeLocaleValue(Locale locale) {
		getLocaleMap().remove(locale);
	}

	@Override
	public TLocaleValue getLocaleValueOrDefault(Locale locale) {
		if (localeMap == null || getLocaleMap().size() == 0 || locale == null || locale.getDisplayName() == null)
			return getDefaultLocale();

		if (getLocaleMap().containsKey(locale))
			return getLocaleMap().get(locale);

		return getDefaultLocale();
	}

	@Override
	public TLocaleValue getCurrentLocaleValue() {
		return getLocaleValueOrDefault(Locale.getDefault());
	}
}
