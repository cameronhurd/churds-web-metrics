package churd.metrics;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.servlet.FilterChain;
import javax.servlet.ServletResponseWrapper;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;

@ExtendWith(MockitoExtension.class)
public class WebMetricsServletFilterTest {

    @Mock
    private HttpServletRequest _httpServletRequest;
    @Mock
    private HttpServletResponse _httpServletResponse;
    @Mock
    private FilterChain _filterChain;
    @Mock
    private MetricsService _metricsService;

    private WebMetricsServletFilter _servletFilter;

    @BeforeEach
    public void setupTestClass() {
        _servletFilter = new WebMetricsServletFilter();
        _servletFilter.setMetricsService(_metricsService);
    }

    @Test
    @DisplayName("Metrics ID is set on the response header")
    public void testDoFilterResponseHeaderMetricsId() throws Exception {
        _servletFilter.doFilter(_httpServletRequest, _httpServletResponse, _filterChain);

        ArgumentCaptor<String> headerKeyCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> headerValueCaptor = ArgumentCaptor.forClass(String.class);
        verify(_httpServletResponse).addHeader(headerKeyCaptor.capture(), headerValueCaptor.capture());
        assertEquals(WebMetricsServletFilter.METRICS_ID, headerKeyCaptor.getValue());
        assertNotNull(headerValueCaptor.getValue());
    }

    @Test
    @DisplayName("ResponseWrapper is provided to the filter chain")
    public void testDoFilterResponseWrapper() throws Exception {
        _servletFilter.doFilter(_httpServletRequest, _httpServletResponse, _filterChain);

        verify(_filterChain).doFilter(eq(_httpServletRequest), isA(ResponseWrapper.class));
    }

    @Test
    @DisplayName("Metric is updated with response time")
    public void testDoFilterResponseTimeMetric() throws Exception {
        _servletFilter.doFilter(_httpServletRequest, _httpServletResponse, _filterChain);

        ArgumentCaptor<WebMetric> metricCaptor = ArgumentCaptor.forClass(WebMetric.class);
        verify(_metricsService).updateMetric(metricCaptor.capture());
        WebMetric savedMetric = metricCaptor.getValue();
        assertNotNull(savedMetric.getId());
        assertTrue(savedMetric.getRequestTimeNanos() > 0);
    }

    @Test
    @DisplayName("Non HttpServletResponse is forwarded along filter chain")
    public void testDoFilterNonHttpServletResponse() throws Exception {
        ServletResponseWrapper wrapper = new ServletResponseWrapper(_httpServletResponse);
        _servletFilter.doFilter(_httpServletRequest, wrapper, _filterChain);

        verify(_filterChain).doFilter(eq(_httpServletRequest), isA(ServletResponseWrapper.class));
    }
}
