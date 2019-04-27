package churd.metrics;

import org.omg.CORBA.ARG_OUT;
import org.omg.PortableServer.REQUEST_PROCESSING_POLICY_ID;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;

public class ResponseWrapper extends HttpServletResponseWrapper {

    final ByteArrayOutputStream _byteArrayOutputStream;
    final PrintWriter _printWriter;

    public ResponseWrapper(HttpServletResponse response) {
        super(response);
        _byteArrayOutputStream = new ByteArrayOutputStream();
        _printWriter = new PrintWriter(_byteArrayOutputStream);
    }

    public ByteArrayOutputStream getByteArrayOutputStream() {
        return _byteArrayOutputStream;
    }

    @Override
    public PrintWriter getWriter() {
        return _printWriter;
    }

    @Override
    public ServletOutputStream getOutputStream() {
        return new ServletOutputStream() {
            @Override
            public void write(int b) {
                _byteArrayOutputStream.write(b);
            }
        };
    }
}
