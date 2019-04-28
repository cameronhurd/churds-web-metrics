package churd.metrics;

/**
 * Service that provides access to Web Metrics
 */
public interface MetricsService {

    /**
     * Add or update a WebMetric
     *
     * @param metric
     */
    void updateMetric(WebMetric metric);

    /**
     * Retrieves current aggregate response times in nanoseconds.
     *
     * @return AggregateMetric object
     */
    AggregateMetric getAggregateResponseTimeNanos();

    /**
     * Retrieves current aggregate response sizes in bytes
     *
     * @return AggregateMetric object
     */
    AggregateMetric getAggregateResponseSizeBytes();

    /**
     * Retrieve a Web Metric by ID
     *
     * @param id - the Web Metric ID
     * @return WebMetric object, null if not found
     */
    WebMetric getWebMetricById(String id);
}
