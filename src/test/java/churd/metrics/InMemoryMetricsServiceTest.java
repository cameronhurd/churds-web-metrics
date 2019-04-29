package churd.metrics;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class InMemoryMetricsServiceTest {

    private final MetricsService _metricsService = InMemoryMetricsService.getInstance();

    @Test
    @DisplayName("Test add response time metric")
    public void testUpdateMetricResponseTimeNew() {
        InMemoryMetricsService.clearMetrics();
        String metricId = "addT1";
        _metricsService.setResponseTimeMetric(metricId, 20L);
        WebMetric retrieved = _metricsService.getWebMetricById(metricId);
        assertEquals(Long.valueOf(20), retrieved.getRequestTimeNanos());
    }

    @Test
    @DisplayName("Test add response size metric")
    public void testUpdateMetricResponseSizeNew() {
        InMemoryMetricsService.clearMetrics();
        String metricId = "addS1";
        _metricsService.updateResponseSizeMetric(metricId, 20L);
        WebMetric retrieved = _metricsService.getWebMetricById(metricId);
        assertEquals(Long.valueOf(20), retrieved.getResponseByteCount());
    }

    @Test
    @DisplayName("Test update response size metric")
    public void testUpdateMetricResponseSizeUpdate() {
        InMemoryMetricsService.clearMetrics();
        String metricId = "updateS1";
        _metricsService.updateResponseSizeMetric(metricId, 20L);
        _metricsService.updateResponseSizeMetric(metricId, 30L);
        WebMetric retrieved = _metricsService.getWebMetricById(metricId);
        assertEquals(Long.valueOf(30L), retrieved.getResponseByteCount());
    }

    @Test
    @DisplayName("Get metric by ID retrieves the correct metric")
    public void testGetMetricById() {
        InMemoryMetricsService.clearMetrics();
        String metricId1 = "byId1";
        _metricsService.updateResponseSizeMetric(metricId1, 20L);
        _metricsService.updateResponseSizeMetric("byId2", 50L);
        WebMetric retrieved = _metricsService.getWebMetricById(metricId1);
        assertEquals(metricId1, retrieved.getId());
    }

    @Test
    @DisplayName("Get aggregate response size is accurate")
    public void testGetAggregateResponseSizeBytes() {
        InMemoryMetricsService.clearMetrics();
        String metric1Id = "resSize1";
        String metric2Id = "resSize2";
        _metricsService.updateResponseSizeMetric(metric1Id, 100L);
        _metricsService.updateResponseSizeMetric(metric2Id, 50L);

        AggregateMetric aggregate = _metricsService.getAggregateResponseSizeBytes();
        assertEquals(Long.valueOf(100), aggregate.getMax());
        assertEquals(Long.valueOf(50), aggregate.getMin());
        assertEquals(Long.valueOf(75), aggregate.getAverage());
    }

    @Test
    @DisplayName("Get aggregate response time is accurate")
    public void testGetAggregateResponseTimeNanos() {
        InMemoryMetricsService.clearMetrics();
        String metric1Id = "resTime1";
        String metric2Id = "resTime2";
        _metricsService.setResponseTimeMetric(metric1Id, 20L);
        _metricsService.setResponseTimeMetric(metric2Id, 40L);

        AggregateMetric aggregate = _metricsService.getAggregateResponseTimeNanos();
        assertEquals(Long.valueOf(40), aggregate.getMax());
        assertEquals(Long.valueOf(20), aggregate.getMin());
        assertEquals(Long.valueOf(30), aggregate.getAverage());
    }
}
