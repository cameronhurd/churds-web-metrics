package churd.metrics.filter;

import churd.metrics.ResponseWrapper;
import churd.metrics.api.InMemoryMetricsService;
import churd.metrics.api.MetricsService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

/**
 * Servlet filter implementation that tracks web metrics
 */
@WebFilter(filterName = "WebMetricsServletFilter", urlPatterns = {"/*"})
public class WebMetricsServletFilter implements Filter {

    private static final Logger _log = LogManager.getLogger(WebMetricsServletFilter.class);

    public static final String METRICS_ID = "churds-metrics-id";

    private MetricsService _metricsService;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        _log.info("Init web filter");
        _metricsService = InMemoryMetricsService.getInstance();
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
            throws IOException, ServletException {

        if (response instanceof HttpServletResponse) {
            String metricId = UUID.randomUUID().toString();
            long startTimeNanos = System.nanoTime();
            ResponseWrapper responseWrapper = new ResponseWrapper((HttpServletResponse) response, _metricsService, metricId);

            responseWrapper.addHeader(METRICS_ID, metricId);
            _log.info("doFilter request - metrics-id: {}", metricId);

            filterChain.doFilter(request, responseWrapper);

            long requestTimeNanos = System.nanoTime() - startTimeNanos;
            _metricsService.setResponseTimeMetric(metricId, requestTimeNanos);
        }
        else {
            filterChain.doFilter(request, response);
        }
    }

    @Override
    public void destroy() {
    }

    protected void setMetricsService(MetricsService metricsService) {
        _metricsService = metricsService;
    }
}



