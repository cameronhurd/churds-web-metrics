package churd.metrics;

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

    private static final String METRICS_ID = "churds-metrics-id";

    private MetricsService _metricsService;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        _log.info("Init web filter");
        _metricsService = InMemoryMetricsService.getInstance();
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
            throws IOException, ServletException {

        _log.info("Request start");
        if (response instanceof HttpServletResponse) {
            WebMetric metric = new WebMetric(UUID.randomUUID().toString());
            metric.startTimer();
            ResponseWrapper responseWrapper = new ResponseWrapper((HttpServletResponse) response, _metricsService, metric);

            responseWrapper.addHeader(METRICS_ID, metric.getId());
            _log.info("doFilter requst - metrics-id: {}", metric.getId());

            _log.info("call doFilter");
            filterChain.doFilter(request, responseWrapper);

            metric.endTimer();
            _metricsService.updateMetric(metric);
        }
        else {
            filterChain.doFilter(request, response);
        }
    }

    @Override
    public void destroy() {
    }
}



