package churd.metrics;

public interface MetricsService {

    void updateMetric(WebMetric metric);

    AggregateMetric getAggregateResponseTimeNanos();

    AggregateMetric getAggregateResponseSizeBytes();

    WebMetric getWebMetricById(String id);
}
