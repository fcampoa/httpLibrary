package org.server;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.util.Request;
import org.util.Response;
import org.util.HttpMethod;
import org.util.Utilities;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;

public abstract class HttpController implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        try {
        HttpMethod method = HttpMethod.valueOf(exchange.getRequestMethod());
            OutputStream out = exchange.getResponseBody();
            InputStreamReader in = new InputStreamReader(exchange.getRequestBody(), "utf-8");
            BufferedReader buffer = new BufferedReader(in);
            List<String> lines = buffer.lines().collect(Collectors.toList());
            StringBuilder sb = new StringBuilder();
            lines.forEach(s -> sb.append(s));
            Request request = new Request(sb.toString(), method.getValue());
            Response response = null;
            switch (method) {
                case GET -> response = get(request);
                case POST -> response = post(request);
                case PUT -> response = put(request);
                case PATCH -> response = patch(request);
                case DELETE -> response = delete(request);
                default -> {
                }
            }
        if (response != null) {
            String json = Utilities.toJson(response);
            exchange.sendResponseHeaders(response.getStatus(), json.length());
            out.write(json.getBytes(StandardCharsets.UTF_8));
        }
        out.close();
        }catch(Exception ex) {
            System.out.println(ex.getMessage());
            throw new IOException(ex.getMessage());
        }
    }
    protected abstract Response get(Request request);
    protected abstract Response post(Request request);
    protected abstract Response put(Request request);
    protected abstract Response patch(Request request);
    protected abstract Response delete(Object id);
    protected abstract Response get(Object id);
}
