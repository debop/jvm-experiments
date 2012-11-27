package kr.ecsp.data.hibernate.tools;

import com.google.common.collect.Sets;
import kr.ecsp.data.domain.model.*;
import kr.escp.commons.DataObject;
import kr.escp.commons.Func1;
import kr.escp.commons.Guard;
import kr.escp.commons.json.GsonSerializer;
import kr.escp.commons.json.JsonSerializer;
import kr.escp.commons.parallelism.Parallels;
import kr.escp.commons.tools.MapperTool;
import kr.escp.commons.tools.ReflectTool;
import kr.escp.commons.tools.StringTool;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import javax.annotation.Nullable;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


/**
 * Hibernate 엔티티 정보를 관리하기 위한 Utility Class 입니다.
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 9. 24
 */
@Slf4j
@SuppressWarnings("unchecked")
public class EntityTool {

	private EntityTool() {}

	public static final String PROPERTY_ANCESTORS = "ancestors";
	public static final String PROPERTY_DESCENDENTS = "descendents";


	@Getter(lazy = true)
	private static final JsonSerializer gsonSerializer = new GsonSerializer();


	public static String entityToString(DataObject entity) {
		return (entity != null) ? ReflectTool.objectToString(entity) : StringTool.NULL_STR;
	}

	public static String asGsonText(DataObject entity) throws Exception {
		return getGsonSerializer().serializeAsText(entity);
	}

	// region << Hierarchy >>

	public static <T extends HierarchyEntity<T>> void assertNotCircularHierarchy(T child, T parent) {
		if (child == parent)
			throw new IllegalArgumentException("Child and Paremt are same.");

		if (child.getDescendents().contains(parent))
			throw new IllegalArgumentException("child 가 parent를 이미 자손으로 가지고 있습니다.");

		if (Sets.intersection(parent.getAncestors(), child.getDescendents()).size() > 0)
			throw new IllegalArgumentException("parent의 조상과 child의 조상이 같은 넘이 있으면 안됩니다.");
	}

	public static <T extends HierarchyEntity<T>> void setHierarchy(T child, T oldParent, T newParent) {
		Guard.shouldNotBeNull(child, "child");

		if (log.isDebugEnabled())
			log.debug("현재 노드의 부모를 변경하고, 계층구조 정보를 변경합니다... child=[{}], oldParent=[{}], newParent=[{}]",
			          child, oldParent, newParent);

		if (oldParent != null)
			removeHierarchy(child, oldParent);

		if (newParent != null)
			setHierarchy(child, newParent);
	}

	public static <T extends HierarchyEntity<T>> void setHierarchy(T child, T parent) {
		if (parent == null || child == null)
			return;

		if (log.isDebugEnabled())
			log.debug("노드의 부모 및 조상을 설정합니다. child=[{}], parent=[{}]", child, parent);

		parent.getDescendents().add(child);
		parent.getDescendents().addAll(child.getDescendents());

		for (T ancestor : parent.getAncestors()) {
			ancestor.getDescendents().add(child);
			ancestor.getDescendents().addAll(child.getDescendents());
		}

		child.getAncestors().add(parent);
		child.getAncestors().addAll(parent.getAncestors());
	}

	public static <T extends HierarchyEntity<T>> void removeHierarchy(T child, T parent) {
		if (parent == null)
			return;

		Guard.shouldNotBeNull(child, "child");

		if (log.isDebugEnabled())
			log.debug("노드의 부모 및 조상을 제거합니다. child=[{}], parent=[{}]", child, parent);


		child.getAncestors().remove(parent);
		child.getAncestors().removeAll(parent.getAncestors());

		for (T ancestor : parent.getAncestors()) {
			ancestor.getDescendents().remove(child);
			ancestor.getDescendents().removeAll(child.getDescendents());
		}
		for (T descendent : child.getDescendents()) {
			descendent.getAncestors().remove(parent);
			descendent.getAncestors().removeAll(parent.getAncestors());
		}
	}

	public static <T extends HierarchyEntity<T> & Entity<TId>, TId extends Serializable>
	DetachedCriteria GetAncestorsCriteria(T entity, Session session, Class<T> entityClass) {
		return
			DetachedCriteria
				.forClass(entityClass)
				.createAlias(PROPERTY_DESCENDENTS, "des")
				.add(Restrictions.eq("des.id", entity.getId()));
	}

	public static <T extends HierarchyEntity<T> & Entity<TId>, TId extends Serializable>
	DetachedCriteria GetDescendentsCriteria(T entity, Session session, Class<T> entityClass) {
		return
			DetachedCriteria.forClass(entityClass)
			                .createAlias(PROPERTY_ANCESTORS, "ans")
			                .add(Restrictions.eq("ans.id", entity.getId()));
	}

	public static <T extends HierarchyEntity<T> & Entity<TId>, TId extends Serializable>
	DetachedCriteria GetAncestorsIdCriteria(T entity, Session session, Class<T> entityClass) {
		return
			GetAncestorsCriteria(entity, session, entityClass)
				.setProjection(Projections.distinct(Projections.id()));
	}

	public static <T extends HierarchyEntity<T> & Entity<TId>, TId extends Serializable>
	DetachedCriteria GetDescendentsIdCriteria(T entity, Session session, Class<T> entityClass) {
		return
			GetDescendentsCriteria(entity, session, entityClass)
				.setProjection(Projections.distinct(Projections.id()));
	}

	// endregion

	// region << LocaleEntity >>

	final static String GET_LIST_BY_LOCALE_KEY =
		"select distinct loen from %s loen where :key in indices (loen.localeMap)";

	final static String GET_LIST_BY_LOCALE_PROPERTY =
		"select distinct loen from %s loen join loen.localeMap locale where locale.%s = :%s";

	public static <T extends LocaleEntity<TLocaleValue>, TLocaleValue extends LocaleValue>
	void CopyLocale(T source, T destination) {
		for (Locale locale : source.getLcoales())
			destination.addLocaleValue(locale, source.getLocaleValue(locale));
	}

	public static <T extends LocaleEntity<TLocaleValue>, TLocaleValue extends LocaleValue>
	List<T> containsLocale(Class<T> entityClass, Locale locale) {

		String hql = String.format(GET_LIST_BY_LOCALE_KEY, entityClass.getName());
		if (log.isDebugEnabled())
			log.debug("Locale[{}]를 가지는 엔티티 조회 hql=[{}]", locale, hql);


//		HibernateRepository<T> repository = HbRepositoryFactory.get(entityClass);
//		return repository.getListByHql(hql, new HibernateParameter("key", locale.getLanguage()));
		// TODO: 구현 중
		return null;
	}

	public static <T extends LocaleEntity<TLocaleValue>, TLocaleValue extends LocaleValue>
	List<T> containsLocale(Class<T> entityClass,
	                       String propertyName,
	                       Object value,
	                       org.hibernate.type.Type type) {

		String hql = String.format(GET_LIST_BY_LOCALE_PROPERTY, entityClass.getName(), propertyName, propertyName);

//		HibernateRepository<T> repository = HbRepositoryFactory.get(entityClass);
//		return repository.getListByHql(hql, new HibernateParameter(propertyName, value, type));
		// TODO : 구현 중
		return null;
	}

	// endregion


	// region << MetaEntity >>

	static final String GET_LIST_BY_META_KEY =
		"select distinct me from %s me where :key in indices(me.metaMap)";
	static final String GET_LIST_BY_META_VALUE =
		"select distinct me from %s me join me.metaMap meta where meta.value = :value";

	public static <T extends MetaEntity> List<T> containsMetaKey(Class<T> entityClass, String key) {
		Guard.shouldNotBeWhiteSpace(key, "key");

		String hql = String.format(GET_LIST_BY_META_KEY, entityClass.getName());

		if (log.isDebugEnabled())
			log.debug("메타데이타 키 [{}] 를 가지는 엔티티 조회 hql=[{}]", key, hql);

//		HibernateRepository<T> repository = HbRepositoryFactory.get(entityClass);
//		return repository.getListByHql(hql, new HibernateParameter("key", key, StringType.INSTANCE));
		// TODO: 구현 중
		return null;
	}

	public static <T extends MetaEntity> List<T> containsMetaValue(Class<T> entityClass, String value) {
		Guard.shouldNotBeWhiteSpace(value, "value");

		String hql = String.format(GET_LIST_BY_META_VALUE, entityClass.getName());
		if (log.isDebugEnabled())
			log.debug("메타데이타 value[{}]를 가지는 엔티티 조회 hql=[{}]", value, hql);

//		HibernateRepository<T> repository = HbRepositoryFactory.get(entityClass);
//		return repository.getListByHql(hql, new HibernateParameter("value", value, StringType.INSTANCE));
		// TODO: 구현 중
		return null;
	}

	// endregion

	// region << Entity Mapper >>

	/**
	 * 원본 엔티티의 속성정보를 대상 엔티티의 속성정보로 매핑시킵니다.
	 */
	public static <S, T> T mapEntity(S source, T target) {
		MapperTool.map(source, target);
		return target;
	}

	public static <S, T> T mapEntity(S source, Class<T> targetClass) {
		Guard.shouldNotBeNull(source, "source");
		return MapperTool.map(source, targetClass);
	}

	/**
	 * 원본 엔티티를 대상 엔티티로 매핑을 수행합니다. {@link kr.escp.commons.tools.MapperTool} 을 사용합니다.
	 */
	public static <S, T> List<T> mapEntities(List<S> sources, List<T> targets) {
		Guard.shouldNotBeNull(sources, "sources");
		Guard.shouldNotBeNull(targets, "targets");

		int size = Math.min(sources.size(), targets.size());

		for (int i = 0; i < size; i++) {
			MapperTool.map(sources.get(i), targets.get(i));
		}
		return targets;
	}

	/**
	 * 병렬 방식으로 원본으로부터 대상엔티티로 매핑합니다. 대용량 정보의 DTO 생성 시 유리합니다.
	 */
	public static <S, T> List<T> mapEntitiesAsParallel(final List<S> sources,
	                                                   final Class<T> targetClass) {
		if (sources == null || sources.size() == 0)
			return new ArrayList<>();

		return
			Parallels.run(sources, new Func1<S, T>() {
				@Override
				public T execute(@Nullable S input) {
					return MapperTool.map(input, targetClass);
				}
			});
	}

	// endregion

	// region << TreeNode >>

	public static <T extends TreeEntity<T>> void updateTreeNodePosition(T entity) {
		Guard.shouldNotBeNull(entity, "entity");

		if (log.isTraceEnabled())
			log.trace("update tree node position... entity=[{}]", entity);

		TreeNodePosition nodePosition = entity.getNodePosition();

		if (entity.getParent() != null) {
			nodePosition.setLevel(entity.getParent().getNodePosition().getLevel() + 1);
			if (!entity.getParent().getChildren().contains(entity))
				nodePosition.setOrder(entity.getParent().getChildren().size());
		} else {
			nodePosition.setLevel(0);
			nodePosition.setOrder(0);
		}
	}

	public static <T extends TreeEntity<T>> Long getChildCount(T entity) {
		if (log.isDebugEnabled())
			log.debug("tree entity의 자식 엔티티의 수를 구합니다. entity=[{}]", entity);

		DetachedCriteria criteria = HibernateTool.createDetachedCriteria(entity.getClass());
		criteria.add(Restrictions.eq("parent", entity));

//		HibernateRepository<T> repository = (HibernateRepository<T>) HbRepositoryFactory.get(entity.getClass());
//		return repository.count(criteria);
		// TODO: 구현 중
		return null;
	}

	public static <T extends TreeEntity<T>> Boolean hasChildren(T entity) {
		if (log.isDebugEnabled())
			log.debug("tree entity 가 자식을 가지는지 확안합니다. entity=[{}]", entity);

		DetachedCriteria criteria = HibernateTool.createDetachedCriteria(entity.getClass());
		criteria.add(Restrictions.eq("parent", entity));

//		HibernateRepository<T> repository = (HibernateRepository<T>) HbRepositoryFactory.get(entity.getClass());
//		return repository.exists(criteria);
		// TODO: 구현 중
		return null;
	}


	// endregion
}
