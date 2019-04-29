package churd.metrics;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;

/**
 * In memory implementation of MetricsService
 */
public class InMemoryMetricsService implements MetricsService {

    private static final Logger _log = LogManager.getLogger(InMemoryMetricsService.class);

    private static InMemoryMetricsService _instance;

    private HashMap<String, WebMetric> _metrics;
    private AggregateMetric _aggregateResponseTimeNanos;
    private AggregateMetric _aggregateResponseSizeBytes;

    private InMemoryMetricsService() {
    }

    public static MetricsService getInstance() {
        if (null == _instance) {
            _instance = new InMemoryMetricsService();
            clearMetrics();
        }
        return _instance;
    }

    @Override
    public WebMetric getWebMetricById(String id) {
        return _metrics.get(id);
    }

    @Override
    public synchronized void setResponseTimeMetric(String metricId, long nanos) {
        WebMetric old = _metrics.get(metricId);

        _log.info("update response time: {}, {}", metricId, nanos);
        WebMetric metric = null == old ? new WebMetric(metricId) : old;
        metric.setRequestTimeNanos(nanos);
        if (old == null) {
            _metrics.put(metricId, metric);
        }

        _aggregateResponseTimeNanos.applyMetricValue(null, metric.getRequestTimeNanos());
    }

    @Override
    public synchronized void updateResponseSizeMetric(String metricId, long bytesToAdd) {
        WebMetric old = _metrics.get(metricId);
        Long oldResponseByteCount = null == old ? null : old.getResponseByteCount();

        _log.info("add bytes: {}, {}", metricId, bytesToAdd);
        WebMetric metric = null == old ? new WebMetric(metricId) : old;
        metric.setResponseByteCount(bytesToAdd);
        if (old == null) {
            _metrics.put(metricId, metric);
        }

        _aggregateResponseSizeBytes.applyMetricValue(oldResponseByteCount, metric.getResponseByteCount());
    }

    @Override
    public AggregateMetric getAggregateResponseSizeBytes() {
        return _aggregateResponseSizeBytes;
    }

    @Override
    public AggregateMetric getAggregateResponseTimeNanos() {
        return _aggregateResponseTimeNanos;
    }

    /**
     * Clear all metrics that are in the in memory store
     */
    public static void clearMetrics() {
        _instance._metrics = new HashMap<>();
        _instance._aggregateResponseSizeBytes = new AggregateMetric();
        _instance._aggregateResponseTimeNanos = new AggregateMetric();
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("AllMetrics{");
        sb.append("_metrics=").append(_metrics);
        sb.append(", _aggregateResponseTimeNanos=").append(_aggregateResponseTimeNanos);
        sb.append(", _aggregateResponseSizeBytes=").append(_aggregateResponseSizeBytes);
        sb.append('}');
        return sb.toString();
    }
}
