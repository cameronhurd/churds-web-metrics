package churd.metrics.api;

/**
 * Tracks metrics for a HTTP request/response
 */
public class WebMetric {
    private final String _id;
    private Long _responseByteCount;
    private Long _requestTimeNanos;

    public WebMetric(String id) {
        _id = id;
    }

    public String getId() {
        return _id;
    }

    public Long getResponseByteCount() {
        return _responseByteCount;
    }

    public Long getRequestTimeNanos() {
        return _requestTimeNanos;
    }

    public void setResponseByteCount(Long responseByteCount) {
        _responseByteCount = responseByteCount;
    }

    public void setRequestTimeNanos(Long requestTimeNanos) {
        _requestTimeNanos = requestTimeNanos;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("WebMetric{");
        sb.append("_id='").append(_id).append('\'');
        sb.append(", _responseByteCount=").append(_responseByteCount);
        sb.append(", _requestTimeNanos=").append(_requestTimeNanos);
        sb.append('}');
        return sb.toString();
    }
}
