package churd.metrics;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class WebMetricTest {

    @Test
    @DisplayName("Cloning a web metric copies all fields")
    public void testCloneWebMetric() {
        WebMetric metricA = new WebMetric("idA");
        metricA.setResponseByteCount(3001L);
        metricA.startTimer();
        metricA.endTimer();
        WebMetric metricACopy = new WebMetric(metricA);

        assertNotEquals(metricACopy, metricA);
        assertEquals(metricA.getId(), metricACopy.getId());
        assertEquals(metricA.getRequestTimeNanos(), metricACopy.getRequestTimeNanos());
        assertEquals(metricA.getStartTimeNanos(), metricACopy.getStartTimeNanos());
        assertEquals(metricA.getResponseByteCount(), metricACopy.getResponseByteCount());
    }
}
