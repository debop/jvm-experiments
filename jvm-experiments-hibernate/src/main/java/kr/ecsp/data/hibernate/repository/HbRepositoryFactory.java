package kr.ecsp.data.hibernate.repository;

import kr.ecsp.data.domain.model.StatefulEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;


/**
 * Hibernate용 Repository인 {@link HibernateRepository}, {@link SimpleHibernateRepository} 를 생성해주는 Factory입니다.
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 9. 25
 */
@Slf4j
public class HbRepositoryFactory {

	@Autowired static ApplicationContext springContext;

	private HbRepositoryFactory() {}

	/**
	 * 해당 수형의 Entity를 다루는 {@link HibernateRepository} 를 생성합니다.
	 */
	@SuppressWarnings("unchecked")
	public static synchronized <E extends StatefulEntity> HibernateRepository<E> get(Class<E> entityClass) {

		if (log.isDebugEnabled())
			log.debug("HibernateRepository<{}> 를 생성합니다.", entityClass.getSimpleName());

		//
		// TODO: 이건 SpringTool 을 만들어서 제공해야 한다.
		//
		String beanName = "Repository." + entityClass.getName();
		Object bean = null;
		try {
			bean = springContext.getBean(beanName);
		} catch (Exception e) {
			if (log.isWarnEnabled())
				log.warn("[{}] 의 Bean이 등록되지 않았습니다.", beanName);
		}

		if (bean == null) {
			bean = new SimpleHibernateRepository<E>();
			DefaultListableBeanFactory beanFactory =
				(DefaultListableBeanFactory) ((AbstractApplicationContext) springContext).getBeanFactory();
			BeanDefinition beanDef = BeanDefinitionBuilder
				.rootBeanDefinition(bean.getClass())
				.getBeanDefinition();
			beanDef.setScope("prototype");

			beanFactory.registerBeanDefinition(beanName, beanDef);
		}
		return (HibernateRepository<E>) bean;
	}
}
