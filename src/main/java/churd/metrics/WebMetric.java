package churd.metrics;

public class WebMetric {
    private final String _id;
    private final long _responseByteCount;
    private final long _requestTimeNanos;

    public WebMetric(String id, long responseByteCount, long requestTimeNanos) {
        _id = id;
        _responseByteCount = responseByteCount;
        _requestTimeNanos = requestTimeNanos;
    }

    public String getId() {
        return _id;
    }

    public long getResponseByteCount() {
        return _responseByteCount;
    }

    public long getRequestTimeNanos() {
        return _requestTimeNanos;
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
