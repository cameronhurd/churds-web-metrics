package churd.metrics;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;

public class InMemoryMetricsService implements MetricsService {

    private static final Logger _log = LogManager.getLogger(InMemoryMetricsService.class);

    private final HashMap<String, WebMetric> _metrics;
    private final AggregateMetric _aggregateResponseTimeNanos;
    private final AggregateMetric _aggregateResponseSizeBytes;

    public InMemoryMetricsService() {
        _metrics = new HashMap<>();

        _aggregateResponseSizeBytes = new AggregateMetric();
        _aggregateResponseTimeNanos = new AggregateMetric();
    }

    public synchronized void updateMetric(WebMetric metric) {
        WebMetric old = _metrics.get(metric.getId());
        Long oldRequestTimeNanos = null == old ? null : old.getRequestTimeNanos();
        Long oldResponseByteCount = null == old ? null : old.getResponseByteCount();

        _log.info("update metric: {}", metric);
        WebMetric metricCopy = new WebMetric(metric);
        _metrics.put(metric.getId(), metricCopy);

        _aggregateResponseTimeNanos.applyMetricValue(oldRequestTimeNanos, metric.getRequestTimeNanos());
        _aggregateResponseSizeBytes.applyMetricValue(oldResponseByteCount, metric.getResponseByteCount());
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
