package org.controllers;

import org.server.HttpController;
import org.util.Request;
import org.util.Response;

public class HomeController extends HttpController {
    @Override
    protected Response get(Request request) {
        StringBuilder sb = new StringBuilder();
        sb.append("Hello from home controller");
        return new Response(sb.toString());
    }

    @Override
    protected Response post(Request request) {
        return null;
    }

    @Override
    protected Response put(Request request) {
        return null;
    }

    @Override
    protected Response patch(Request request) {
        return null;
    }

    @Override
    protected Response delete(Object id) {
        return null;
    }

    @Override
    protected Response get(Object id) {
        return null;
    }
}
