package kr.nsoft.data.hibernate.springmvc;

import kr.nsoft.data.hibernate.unitofwork.IUnitOfWork;
import kr.nsoft.data.hibernate.unitofwork.UnitOfWorks;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Spring MVC 에서 servlet 시작과 완료 시에 UnitOfWork를 시작하고, 완료하도록 합니다.
 * User: sunghyouk.bae@gmail.com
 * Date: 13. 2. 15.
 */
@Slf4j
public class UnitOfWorkInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {

        UnitOfWorks.start();
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request,
                           HttpServletResponse response,
                           Object handler,
                           ModelAndView modelAndView) throws Exception {
        // Nothing to do
    }

    @Override
    public void afterCompletion(HttpServletRequest request,
                                HttpServletResponse response,
                                Object handler,
                                Exception ex) throws Exception {
        IUnitOfWork unitOfWork = UnitOfWorks.getCurrent();
        if (unitOfWork != null)
            UnitOfWorks.closeUnitOfWork(unitOfWork);
    }
}
