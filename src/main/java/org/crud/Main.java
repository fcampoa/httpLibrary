package org.crud;

import com.sun.net.httpserver.HttpServer;
import org.controllers.HomeController;
import org.controllers.PersonController;
import org.util.Container;

import java.net.InetSocketAddress;

public class Main {
    public static void main(String[] args) {
        try {
            int port = 5000;
            HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
            System.out.println("server started at " + port);
            server.createContext("/", new HomeController());
            server.createContext("/person", new PersonController(Container.getPersonRepo()));
            server.setExecutor(null);
            server.start();
        }
        catch(Exception ex) {
            ex.printStackTrace();
        }
    }
}