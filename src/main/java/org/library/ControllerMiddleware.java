package org.library;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import org.util.HttpMethod;
import org.util.Request;
import org.util.Response;
import org.util.Utilities;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

public class ControllerMiddleware implements HttpHandler {
    private Class controller;
    private Object instance;
    private Method[] methods;

    private String rootPath;
    public ControllerMiddleware(Class controller, String rootPath) {
        this.controller = controller;
        this.rootPath = rootPath;
        try {
            initController();
        }catch(Exception ex) {
            ex.printStackTrace();
        }

    }
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
            Map<String, Object> params = new HashMap<>();
            String path = "";
            parseRouteParams(exchange.getRequestURI().getPath(), path, params );
            if(sb.length() > 0) {
                params.putAll(Utilities.toMap(sb.toString()));
            }
            Method m = redirectMethod(method, exchange.getRequestURI().getPath(), path);
            if (m != null) {
                response = invoke(m, params);
            }
            if (response != null) {
                String json = Utilities.toJson(response);
                exchange.sendResponseHeaders(200, json.length());
                out.write(json.getBytes(StandardCharsets.UTF_8));
            }
            out.close();
        }catch(Exception ex) {
            System.out.println(ex.getMessage());
            throw new IOException(ex.getMessage());
        }
    }
    private void initController() throws Exception {
        instance = Container.getInstance(controller);
        methods = instance.getClass().getDeclaredMethods();
        Field[] fields = instance.getClass().getDeclaredFields();
        for(Field field : fields) {
            field.setAccessible(true);
            if(field.isAnnotationPresent(AutoWired.class)) {
                field.set(instance, Container.getInstance(field.getType()));
            }
        }
    }

    private Response invoke(Method method, Map<String, Object> paramsValues) throws Exception {
        Object response = null;
        if (method.getParameterCount() > 0) {
            Parameter[] parameters = method.getParameters();
            List<Object> values = new ArrayList<>();
            for(Parameter param : parameters) {
                Annotation[] annotations = param.getAnnotations();
                for(Annotation annotation : annotations) {
                    if (annotation instanceof PathParam) {
                        PathParam p = (PathParam) annotation;
                        values.add(paramsValues.get(p.name()));
                    }
                    if (annotation instanceof FromBody) {
                        FromBody b = (FromBody) annotation;
                        String v = paramsValues.get(b.name()).toString();
                        Class c = param.getType();
                       values.add(Utilities.fromJson("", param.getClass()));
                    }
                }
            }
            response = method.invoke(instance, values);
        }
        if (response != null && response instanceof Response) {
            return (Response) response;
        }
        return new Response(method.invoke(instance));
    }
    private void parseRouteParams(String originalPath, String actualPath, Map<String, Object> params) {
        String[] expectedParams = originalPath.split("/");
        String[] actualParams = actualPath.split("/");
        for(int i = 0; i < expectedParams.length; i++) {
            if (expectedParams[i].startsWith("{")) {
                params.put(expectedParams[i].replace("{", "").replace("}", ""), actualParams[i]);
            }
        }
    }
    private Method redirectMethod(HttpMethod method, String path, String outPath) {
        List<Method> filteredMethods = null;
        switch(method) {
            case GET:
                filteredMethods = Arrays.stream(methods).filter(m -> m.isAnnotationPresent(GET.class)).collect(Collectors.toList());
                for(Method m : filteredMethods) {
                    GET annotation = m.getAnnotation(GET.class);
                    String p = rootPath + annotation.path();
                    if (p.split("/").length == path.split("/").length){
                        if(p.split("/")[0].equals(path.split("/")[0])) {
                            outPath = p;
                            return m;
                        }
                    }
                }
                break;
            case POST:
                filteredMethods = Arrays.stream(methods).filter(m -> m.isAnnotationPresent(POST.class)).collect(Collectors.toList());
                for(Method m : filteredMethods) {
                    POST annotation = m.getAnnotation(POST.class);
                    String p = rootPath + annotation.path();
                    if (p.split("/").length == path.split("/").length){
                        if(p.split("/")[0].equals(path.split("/")[0])) {
                            outPath = p;
                            return m;
                        }
                    }
                }
                break;
            case PUT:
                filteredMethods = Arrays.stream(methods).filter(m -> m.isAnnotationPresent(PUT.class)).collect(Collectors.toList());
                for(Method m : filteredMethods) {
                    PUT annotation = m.getAnnotation(PUT.class);
                    String p = rootPath + annotation.path();
                    if (p.split("/").length == path.split("/").length) {
                        if(p.split("/")[0].equals(path.split("/")[0])) {
                            outPath = p;
                            return m;
                        }
                    }
                }
                break;
            case PATCH:
                filteredMethods = Arrays.stream(methods).filter(m -> m.isAnnotationPresent(PATCH.class)).collect(Collectors.toList());
                for(Method m : filteredMethods) {
                    PATCH annotation = m.getAnnotation(PATCH.class);
                    String p = rootPath + annotation.path();
                    if (p.split("/").length == path.split("/").length){
                        if(p.split("/")[0].equals(path.split("/")[0])) {
                            outPath = p;
                            return m;
                        }
                    }
                }
                break;
        }
        return null;
    }
}
