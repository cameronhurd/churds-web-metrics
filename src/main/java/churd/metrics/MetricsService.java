package churd.metrics;

/**
 * Service that provides access to Web Metrics
 */
public interface MetricsService {

    /**
     * Set the response time for a given metric ID.
     *
     * @param metricId
     * @param nanos - response time nano seconds
     */
    void setResponseTimeMetric(String metricId, long nanos);

    /**
     * Update response size for the provided metric ID.
     *
     * @param metricId
     * @param bytesToAdd - response bytes
     */
    void updateResponseSizeMetric(String metricId, long bytesToAdd);

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
