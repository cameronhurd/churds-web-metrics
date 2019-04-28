package churd.metrics;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;

public class InMemoryMetricsService implements MetricsService {

    private static final Logger _log = LogManager.getLogger(InMemoryMetricsService.class);

    private static MetricsService _instance;

    private HashMap<String, WebMetric> _metrics;
    private AggregateMetric _aggregateResponseTimeNanos;
    private AggregateMetric _aggregateResponseSizeBytes;

    private InMemoryMetricsService() {
    }

    public static MetricsService getInstance() {
        if (null == _instance) {
            InMemoryMetricsService instance = new InMemoryMetricsService();
            instance._metrics = new HashMap<>();
            instance._aggregateResponseSizeBytes = new AggregateMetric();
            instance._aggregateResponseTimeNanos = new AggregateMetric();

            _instance = instance;
        }
        return _instance;
    }

    @Override
    public WebMetric getWebMetricById(String id) {
        return _metrics.get(id);
    }

    @Override
    public synchronized void updateMetric(WebMetric metric) {
        WebMetric old = _metrics.get(metric.getId());
        Long oldRequestTimeNanos = null == old ? null : old.getRequestTimeNanos();
        Long oldResponseByteCount = null == old ? null : old.getResponseByteCount();

        _log.info("update metric: {}", metric);
        WebMetric metricCopy = new WebMetric(metric);
        _metrics.put(metric.getId(), metricCopy);

        _aggregateResponseTimeNanos.applyMetricValue(oldRequestTimeNanos, metricCopy.getRequestTimeNanos());
        _aggregateResponseSizeBytes.applyMetricValue(oldResponseByteCount, metricCopy.getResponseByteCount());
    }

    @Override
    public AggregateMetric getAggregateResponseSizeBytes() {
        return _aggregateResponseSizeBytes;
    }

    @Override
    public AggregateMetric getAggregateResponseTimeNanos() {
        return _aggregateResponseTimeNanos;
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
