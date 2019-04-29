package churd.metrics;

import churd.metrics.api.MetricsService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ResponseWrapperTest {

    private HttpServletResponse _httpServletResponse;
    private MetricsService _metricsService;

    @Test
    @DisplayName("Wrapping a response updates response size metric")
    public void testWrapResponse() throws Exception {
        _setup();

        when(_httpServletResponse.getOutputStream()).thenReturn(mock(ServletOutputStream.class));
        String metricId = "testId";
        ResponseWrapper wrapper = new ResponseWrapper(_httpServletResponse, _metricsService, metricId);
        wrapper.getOutputStream().write(1);
        wrapper.getOutputStream().write(2);
        wrapper.getOutputStream().flush();

        verify(_metricsService).updateResponseSizeMetric(metricId, 2);
    }

    private void _setup() {
        _httpServletResponse = mock(HttpServletResponse.class);
        _metricsService = mock(MetricsService.class);
    }
}
