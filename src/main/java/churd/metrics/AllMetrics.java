package churd.metrics;

import java.util.HashMap;

public class AllMetrics {
    private final HashMap<String, WebMetric> _metrics;
    private final AggregateMetric _aggregateResponseTimeNanos;
    private final AggregateMetric _aggregateResponseSizeBytes;

    public AllMetrics() {
        _metrics = new HashMap<>();

        _aggregateResponseSizeBytes = new AggregateMetric();
        _aggregateResponseTimeNanos = new AggregateMetric();
    }

    public synchronized void addMetric(WebMetric metric) {
        _metrics.put(metric.getId(), metric);

        _aggregateResponseTimeNanos.applyMetricValue(metric.getRequestTimeNanos());
        _aggregateResponseSizeBytes.applyMetricValue(metric.getResponseByteCount());
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
