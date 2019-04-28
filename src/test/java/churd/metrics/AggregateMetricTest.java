package churd.metrics;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class AggregateMetricTest {

    @Test
    @DisplayName("Null newValue does not affect average")
    public void testApplyMetricValueNullNew() {
        AggregateMetric aggregateMetric = new AggregateMetric();
        aggregateMetric.applyMetricValue(null, 10L);
        aggregateMetric.applyMetricValue(null, null);
        assertEquals(10, aggregateMetric.getAverage());
    }

    @Test
    @DisplayName("Null oldValue applies entire newValue to average")
    public void testApplyMetricValueNullOld() {
        AggregateMetric aggregateMetric = new AggregateMetric();
        aggregateMetric.applyMetricValue(null, 10L);
        aggregateMetric.applyMetricValue(null, 20L);
        assertEquals(15, aggregateMetric.getAverage());
    }

    @Test
    @DisplayName("Null newValue does not effect min or max")
    public void testApplyMetricValueNullNewMinMax() {
        AggregateMetric aggregateMetric = new AggregateMetric();
        aggregateMetric.applyMetricValue(null, null);
        assertNull(aggregateMetric.getMin());
        assertNull(aggregateMetric.getMax());
    }

    @Test
    @DisplayName("Non-null oldValue applies difference to average")
    public void testApplyMetricDifferenceToAverage() {
        // TODO:
    }

    @Test
    @DisplayName("Maximum is applied correctly")
    public void testApplyMetricValueMax() {
        AggregateMetric aggregateMetric = new AggregateMetric();
        aggregateMetric.applyMetricValue(null, 10L);
        aggregateMetric.applyMetricValue(10L, 20L);
        assertEquals(20, aggregateMetric.getMax());
    }

    @Test
    @DisplayName("Minimum is applied correctly")
    public void testApplyMetricValueMin() {
        AggregateMetric aggregateMetric = new AggregateMetric();
        aggregateMetric.applyMetricValue(null, 10L);
        aggregateMetric.applyMetricValue(10L, 20L);
        assertEquals(10, aggregateMetric.getMin());
    }
}
