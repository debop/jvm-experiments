package kr.kth.data.hibernate.interceptor;

import com.google.common.collect.Lists;
import kr.kth.commons.parallelism.AsyncTool;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.EmptyInterceptor;
import org.hibernate.Interceptor;
import org.hibernate.type.Type;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

/**
 * 복수 개의 Interceptor들을 병렬 방식으로 수행하도록 하는 Interceptor입니다.
 * User: sunghyouk.bae@gmail.com
 * Date: 12. 9. 23.
 */
@Slf4j
public class MultiInterceptor extends EmptyInterceptor {

    private static final long serialVersionUID = 4354823013240170534L;

    @Getter
    @Setter
    private List<Interceptor> interceptors;

    public MultiInterceptor() {
    }

    public MultiInterceptor(List<Interceptor> interceptors) {
        this.interceptors = interceptors;
    }

    @Override
    public void onDelete(Object entity, Serializable id, Object[] state, String[] propertyNames, Type[] types) {

        if (interceptors == null || interceptors.size() == 0)
            return;

        final Object entity1 = entity;
        final Serializable id1 = id;
        final Object[] state1 = state;
        final String[] propertyNames1 = propertyNames;
        final Type[] types1 = types;

        List<FutureTask<Void>> tasks = Lists.newLinkedList();

        for (final Interceptor interceptor : interceptors) {

            if (log.isDebugEnabled())
                log.debug("인터셉터의 onDelete메소드를 멀티캐스트로 수행합니다. interceptor=[{}]", interceptor);

            FutureTask<Void> task = AsyncTool.newTask(new Callable<Void>() {
                @Override
                public Void call() throws Exception {
                    interceptor.onDelete(entity1, id1, state1, propertyNames1, types1);
                    return null;
                }
            });
            tasks.add(task);
        }

        try {
            AsyncTool.getAll(tasks);
        } catch (Exception ex) {
            log.error("Interceptor.onDelete 작업 시 예외가 발생했습니다.", ex);
        }
    }

    @Override
    public boolean onFlushDirty(Object entity,
                                Serializable id,
                                Object[] currentState,
                                Object[] previousState,
                                String[] propertyNames,
                                Type[] types) {
        if (interceptors == null || interceptors.size() == 0)
            return false;

        final Object entity1 = entity;
        final Serializable id1 = id;
        final Object[] currentState1 = currentState;
        final Object[] previousState1 = previousState;
        final String[] propertyNames1 = propertyNames;
        final Type[] types1 = types;

        List<FutureTask<Boolean>> tasks = Lists.newLinkedList();

        for (final Interceptor interceptor : interceptors) {

            if (log.isDebugEnabled())
                log.debug("인터셉터의 onFlush 메소드를 멀티캐스트로 수행합니다. interceptor=[{}]", interceptor);

            FutureTask<Boolean> task = AsyncTool.newTask(new Callable<Boolean>() {
                @Override
                public Boolean call() throws Exception {
                    return interceptor.onFlushDirty(entity1,
                            id1,
                            currentState1,
                            previousState1,
                            propertyNames1,
                            types1);
                }
            });
            tasks.add(task);
        }

        try {
            List<Boolean> results = AsyncTool.getAll(tasks);
            return !(results.contains(Boolean.FALSE));
        } catch (Exception ex) {
            if (log.isDebugEnabled())
                log.error("Interceptor.onFlush 작업 시 예외가 발생했습니다.", ex);
        }
        return false;
    }

    @Override
    public boolean onLoad(Object entity, Serializable id, Object[] state, String[] propertyNames, Type[] types) {
        if (interceptors == null || interceptors.size() == 0)
            return false;

        final Object entity1 = entity;
        final Serializable id1 = id;
        final Object[] state1 = state;
        final String[] propertyNames1 = propertyNames;
        final Type[] types1 = types;

        List<FutureTask<Boolean>> tasks = Lists.newLinkedList();

        for (final Interceptor interceptor : interceptors) {

            if (log.isDebugEnabled())
                log.debug("인터셉터의 onLoad 메소드를 멀티캐스트로 수행합니다. interceptor=[{}]", interceptor);

            FutureTask<Boolean> task = AsyncTool.newTask(new Callable<Boolean>() {
                @Override
                public Boolean call() throws Exception {
                    return interceptor.onLoad(entity1, id1, state1, propertyNames1, types1);
                }
            });
            tasks.add(task);
        }

        try {
            List<Boolean> results = AsyncTool.getAll(tasks);
            return !(results.contains(Boolean.FALSE));
        } catch (Exception ex) {
            log.error("Interceptor.onLoad 작업 시 예외가 발생했습니다.", ex);
        }
        return false;
    }

    @Override
    public boolean onSave(Object entity, Serializable id, Object[] state, String[] propertyNames, Type[] types) {
        if (interceptors == null || interceptors.size() == 0)
            return false;

        final Object entity1 = entity;
        final Serializable id1 = id;
        final Object[] state1 = state;
        final String[] propertyNames1 = propertyNames;
        final Type[] types1 = types;

        List<FutureTask<Boolean>> tasks = Lists.newLinkedList();

        for (final Interceptor interceptor : interceptors) {

            if (log.isDebugEnabled())
                log.debug("인터셉터의 onSave 메소드를 멀티캐스트로 수행합니다. interceptor=[{}]", interceptor);

            FutureTask<Boolean> task = AsyncTool.newTask(new Callable<Boolean>() {
                @Override
                public Boolean call() throws Exception {
                    return interceptor.onSave(entity1, id1, state1, propertyNames1, types1);
                }
            });
            tasks.add(task);
        }

        try {
            List<Boolean> results = AsyncTool.getAll(tasks);
            return !(results.contains(Boolean.FALSE));
        } catch (Exception ex) {
            if (log.isErrorEnabled())
                log.error("Interceptor.onSave 작업 시 예외가 발생했습니다.", ex);
        }
        return false;
    }

    @Override
    public void postFlush(Iterator entities) {
        if (interceptors == null || interceptors.size() == 0)
            return;

        final Iterator entities1 = entities;

        List<FutureTask<Void>> tasks = Lists.newLinkedList();

        for (final Interceptor interceptor : interceptors) {

            if (log.isDebugEnabled())
                log.debug("인터셉터의 postFlush 메소드를 멀티캐스트로 수행합니다. interceptor=[{}]", interceptor);

            FutureTask<Void> task = AsyncTool.newTask(new Callable<Void>() {
                @Override
                public Void call() throws Exception {
                    interceptor.postFlush(entities1);
                    return null;
                }
            });
            tasks.add(task);
        }

        try {
            AsyncTool.getAll(tasks);
        } catch (Exception ex) {
            if (log.isErrorEnabled())
                log.error("Interceptor.postFlush 작업 시 예외가 발생했습니다.", ex);
        }
    }

    @Override
    public void preFlush(Iterator entities) {
        if (interceptors == null || interceptors.size() == 0)
            return;

        final Iterator entities1 = entities;

        List<FutureTask<Void>> tasks = Lists.newLinkedList();

        for (final Interceptor interceptor : interceptors) {

            if (log.isDebugEnabled())
                log.debug("인터셉터의 preFlush 메소드를 멀티캐스트로 수행합니다. interceptor=[{}]", interceptor);

            FutureTask<Void> task = AsyncTool.newTask(new Callable<Void>() {
                @Override
                public Void call() throws Exception {
                    interceptor.preFlush(entities1);
                    return null;
                }
            });
            tasks.add(task);
        }

        try {
            AsyncTool.getAll(tasks);
        } catch (Exception ex) {
            if (log.isErrorEnabled())
                log.error("Interceptor.preFlush 작업 시 예외가 발생했습니다.", ex);
        }
    }
}
