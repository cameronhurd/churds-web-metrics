package churd.metrics;

public class WebMetric {
    private final String _id;
    private Long _responseByteCount;
    private Long _requestTimeNanos;
    private Long _startTimeNanos;

    public WebMetric(String id) {
        _id = id;
    }

    public WebMetric(WebMetric metric) {
        _id = metric.getId();
        _responseByteCount = metric.getResponseByteCount();
        _requestTimeNanos = metric.getRequestTimeNanos();
        _startTimeNanos = metric.getStartTimeNanos();
    }

    public Long getStartTimeNanos() {
        return _startTimeNanos;
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

    public void startTimer() {
        _startTimeNanos = System.nanoTime();
    }

    public void endTimer() {
        _requestTimeNanos = System.nanoTime() - _startTimeNanos;
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
