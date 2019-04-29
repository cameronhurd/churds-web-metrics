package churd.metrics;

import churd.metrics.api.MetricsService;
import org.apache.commons.io.output.CountingOutputStream;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * HTTP servlet response wrapper that counts response output stream bytes and reports to MetricsService
 */
public class ResponseWrapper extends HttpServletResponseWrapper {

    private PrintWriter _printWriter;
    private CountingOutputStream _countingOutputStream;
    private ServletOutputStream _servletOutputStream;
    private MetricsService _metricsService;
    private String _metricId;

    public ResponseWrapper(HttpServletResponse response, MetricsService metricsService, String metricId) {
        super(response);
        _metricsService = metricsService;
        _metricId = metricId;
    }

    @Override
    public ServletOutputStream getOutputStream() throws IOException {
        if (null != _servletOutputStream) {
            return _servletOutputStream;
        }

        _countingOutputStream = new CountingOutputStream(super.getOutputStream());
        _servletOutputStream = new ServletOutputStream() {
            @Override
            public void write(int b) throws IOException {
                _countingOutputStream.write(b);
            }

            @Override
            public void flush() throws IOException {
                super.flush();
                _metricsService.updateResponseSizeMetric(_metricId, _countingOutputStream.getByteCount());
            }
        };
        return _servletOutputStream;
    }

    @Override
    public PrintWriter getWriter() throws IOException {
        if (null != _printWriter) {
            return _printWriter;
        }

        _printWriter = new PrintWriter(getOutputStream());
        return _printWriter;
    }
}
