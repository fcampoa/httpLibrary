package org.library;

import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpServer;
import org.exceptions.StartupException;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.util.Set;
import java.util.stream.Collectors;

@configurationClass()
public class ApplicationConfiguration {

    private static HttpServer httpserver;
    private static int port = 5000;

    public static void load(Class applicatioClass, String[] args) throws StartupException{
        String componentsPath = "";
        try{
            httpserver = HttpServer.create(new InetSocketAddress(port), 0);
            System.out.println("server started");
            if (applicatioClass.isAnnotationPresent(StartupClass.class)) {
                StartupClass startup = (StartupClass) applicatioClass.getAnnotation(StartupClass.class);
                String controllersPackage = startup.controllersPackages()[0];
                String repositoriesPackage = startup.repositoriesPackages()[0];
                Set<Class> controllersClasses = findAllClassesUsingClassLoader(controllersPackage);
                Set<Class> repositories = findAllClassesUsingClassLoader(repositoriesPackage);
                loadPackages(repositories.stream().filter(c -> c.isAnnotationPresent(Repository.class)).collect(Collectors.toSet()));
                for(Class c : controllersClasses) {
                    addContext(c);
                }
                httpserver.setExecutor(null);
                httpserver.start();
            }
        } catch(Exception ex) {
            throw new StartupException(ex.getMessage());
        }
    }


    public static Set<Class> findAllClassesUsingClassLoader(String packageName) {
        InputStream stream = ClassLoader.getSystemClassLoader()
                .getResourceAsStream(packageName.replaceAll("[.]", "/"));
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
        return reader.lines()
                .filter(line -> line.endsWith(".class"))
                .map(line -> getClass(line, packageName))
                .collect(Collectors.toSet());
    }

    private static Class getClass(String className, String packageName) {
        try {
            return Class.forName(packageName + "."
                    + className.substring(0, className.lastIndexOf('.')));
        } catch (ClassNotFoundException e) {
            // handle the exception
        }
        return null;
    }

    private static void addContext(Class c) {
        if (c.isAnnotationPresent(RestController.class)) {
            RestController controller = (RestController) c.getAnnotation(RestController.class);
            if (controller.path() != "") {
                httpserver.createContext("/" + controller.path(), new ControllerMiddleware(c,"/" +  controller.path()));
            }
        }
    }
    private static void loadPackages(Set<Class> classes) throws Exception {
        for(Class c : classes) {
            Container.getInstance(c);
        }
    }
}
