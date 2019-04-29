package churd.metrics;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import javax.servlet.FilterChain;
import javax.servlet.ServletResponseWrapper;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class WebMetricsServletFilterTest {

    private HttpServletRequest _httpServletRequest;
    private HttpServletResponse _httpServletResponse;
    private FilterChain _filterChain;
    private MetricsService _metricsService;

    private WebMetricsServletFilter _servletFilter = new WebMetricsServletFilter();

    @Test
    @DisplayName("Metrics ID is set on the response header")
    public void testDoFilterResponseHeaderMetricsId() throws Exception {
        _setup();
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
        _setup();
        _servletFilter.doFilter(_httpServletRequest, _httpServletResponse, _filterChain);

        verify(_filterChain).doFilter(eq(_httpServletRequest), isA(ResponseWrapper.class));
    }

    @Test
    @DisplayName("Metric is updated with response time")
    public void testDoFilterResponseTimeMetric() throws Exception {
        _setup();
        _servletFilter.doFilter(_httpServletRequest, _httpServletResponse, _filterChain);

        ArgumentCaptor<Long> responseTimeCaptor = ArgumentCaptor.forClass(Long.class);
        verify(_metricsService).setResponseTimeMetric(any(String.class), responseTimeCaptor.capture());
        assertTrue(responseTimeCaptor.getValue() > 0);
    }

    @Test
    @DisplayName("Non HttpServletResponse is forwarded along filter chain")
    public void testDoFilterNonHttpServletResponse() throws Exception {
        _setup();
        ServletResponseWrapper wrapper = new ServletResponseWrapper(_httpServletResponse);
        _servletFilter.doFilter(_httpServletRequest, wrapper, _filterChain);

        verify(_filterChain).doFilter(eq(_httpServletRequest), isA(ServletResponseWrapper.class));
    }

    // Issue getting @BeforeEach to work with mockito/junit 5/maven
    private void _setup() {
        _httpServletRequest = mock(HttpServletRequest.class);
        _httpServletResponse = mock(HttpServletResponse.class);
        _filterChain = mock(FilterChain.class);
        _metricsService = mock(MetricsService.class);
        _servletFilter.setMetricsService(_metricsService);
    }
}
