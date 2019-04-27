package churd.metrics.interceptor;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class WebMetricsInterceptor extends HandlerInterceptorAdapter {

    private static final Logger _log = LogManager.getLogger(WebMetricsInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {
        _log.info("Pre handle!");
        /*RequestMapping rm = ((HandlerMethod) handler).getMethodAnnotation(
                RequestMapping.class);

        boolean alreadyLoggedIn = request.getSession()
                .getAttribute("user") != null;
        boolean loginPageRequested = rm != null && rm.value().length > 0
                && "login".equals(rm.value()[0]);

        if (alreadyLoggedIn && loginPageRequested) {
            response.sendRedirect(request.getContextPath() + "/app/main-age");
            return false;
        } else if (!alreadyLoggedIn && !loginPageRequested) {
            response.sendRedirect(request.getContextPath() + "/login");
            return false;
        }*/

        return true;
    }
}
