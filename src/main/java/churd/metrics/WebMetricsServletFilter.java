package churd.metrics;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

@WebFilter(filterName = "WebMetricsServletFilter", urlPatterns = {"/*"})
public class WebMetricsServletFilter implements Filter {

    private static final Logger _log = LogManager.getLogger(WebMetricsServletFilter.class);

    private static final String METRICS_ID = "metrics-id";

    private AllMetrics _allMetrics;

    // https://www.baeldung.com/spring-servletcomponentscan
    // TODO: use webListerner annotation too? or instead?

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        _log.info("Init web filter");
        _allMetrics = new AllMetrics();
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
            throws IOException, ServletException {

        _log.info("Request start");
        if (response instanceof HttpServletResponse) {
            long startNanos = System.nanoTime();
            ResponseWrapper responseWrapper = new ResponseWrapper((HttpServletResponse) response);

            String metricsId = UUID.randomUUID().toString();
            responseWrapper.addHeader(METRICS_ID, metricsId);
            _log.info("doFilter requst - metrics-id: {}", metricsId);
            ServletContextEvent event;

            _log.info("call doFilter");
            filterChain.doFilter(request, responseWrapper);

            byte[] bytes = responseWrapper.getByteArrayOutputStream().toByteArray();
            response.getOutputStream().write(bytes);
            int responseByteLength = null == bytes ? 0 : bytes.length;
            long requestTime = System.nanoTime() - startNanos;
            WebMetric metric = new WebMetric(metricsId, responseByteLength, requestTime);
            _log.info("Request end - metrics: {}", metric);

            _allMetrics.addMetric(metric);
            _log.info("Metric update: {}", _allMetrics);
        }
        else {
            filterChain.doFilter(request, response);
        }
    }

    @Override
    public void destroy() {
    }
}



