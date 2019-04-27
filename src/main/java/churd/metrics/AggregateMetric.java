package churd.metrics;

public class AggregateMetric {
    private Long _average;
    private Long _min;
    private Long _max;
    private long _count = 0;
    private long _totalNanos = 0;

    public long getAverage() {
        return _average;
    }

    public void applyMetricValue(long value) {
        _count++;
        _totalNanos += value;
        _average = _totalNanos / _count;

        if (null == _max || value > _max) {
            _max = value;
        }
        if (null == _min || value < _min) {
            _min = value;
        }
    }

    public void setAverage(long average) {
        _average = average;
    }

    public long getMax() {
        return _max;
    }

    public void setMax(long max) {
        _max = max;
    }

    public long getMin() {
        return _min;
    }

    public void setMin(long min) {
        _min = min;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("AggregateMetric{");
        sb.append("_average=").append(_average);
        sb.append(", _min=").append(_min);
        sb.append(", _max=").append(_max);
        sb.append(", _count=").append(_count);
        sb.append(", _totalNanos=").append(_totalNanos);
        sb.append('}');
        return sb.toString();
    }
}
