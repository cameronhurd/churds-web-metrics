package churd.metrics;

public class AggregateMetric {
    private Long _average;
    private Long _min;
    private Long _max;
    private long _count = 0;
    private long _totalNanos = 0;

    public void applyMetricValue(Long oldValue, Long newValue) {
        if (null == newValue) {
            return;
        }
        if (null == oldValue) {
            _count++;
            _totalNanos += newValue;
        }
        else {
            _totalNanos += newValue - oldValue;
        }
        _average = _totalNanos / _count;

        if (null == _max || newValue > _max) {
            _max = newValue;
        }
        if (null == _min || newValue < _min) {
            _min = newValue;
        }
    }

    public Long getAverage() {
        return _average;
    }

    public Long getMax() {
        return _max;
    }

    public Long getMin() {
        return _min;
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
