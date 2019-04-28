package churd.metrics;

import org.apache.commons.io.output.CountingOutputStream;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.IOException;
import java.io.PrintWriter;

public class ResponseWrapper extends HttpServletResponseWrapper {

    private PrintWriter _printWriter;
    private CountingOutputStream _countingOutputStream;
    private ServletOutputStream _servletOutputStream;
    private MetricsService _metricsService;
    private WebMetric _webMetric;

    public ResponseWrapper(HttpServletResponse response, MetricsService metricsService, WebMetric webMetric) {
        super(response);
        _metricsService = metricsService;
        _webMetric = webMetric;
    }

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
                _webMetric.setResponseByteCount(_countingOutputStream.getByteCount());
                _metricsService.updateMetric(_webMetric);
            }
        };
        return _servletOutputStream;
    }

    public PrintWriter getWriter() throws IOException {
        if (null != _printWriter) {
            return _printWriter;
        }

        _printWriter = new PrintWriter(getOutputStream());
        return _printWriter;
    }
}
