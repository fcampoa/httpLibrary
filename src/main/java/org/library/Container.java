package org.library;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Container {
    private static List<Object> instances;

    private static void addSingleton(Object o) throws Exception {
            if (instances == null) {
                instances = new ArrayList<>();
            }
            instances.add(o);
    }
    private static Object containsInstance(Class c) {
        Object obj = instances.stream().filter(cc -> cc.getClass().getName().equals(c.getName())).findFirst().orElse(null);
        if (obj != null) {
            if (isGeneric(c) != isGeneric(obj.getClass())) {
                obj = null;
            }
        }
        return obj;
    }
    public static Object getInstance(Class c) throws Exception {
        if (instances == null) {
            instances = new ArrayList<>();
        }
        if (c.isInterface()) {
            return getInterfaceInstance(c);
        }
        Object instance = null;
       if ((instance = containsInstance(c)) == null) {
           instance = c.newInstance();
           addSingleton(instance);
       }
       return instance;
    }

    private static Class isGeneric(Class c) {
        Type t = c.getGenericSuperclass();
        if (t == Object.class) {
            return null;
        }
        ParameterizedType pt = (ParameterizedType) t;
        return (Class) pt.getActualTypeArguments()[0];
    }

    private static Object getInterfaceInstance(Class in) {
        List<Object> objects = instances.stream().filter(o -> Arrays.asList(o.getClass().getInterfaces()).contains(in)).collect(Collectors.toList());
        return (!objects.isEmpty()) ? objects.get(0) : null;
    }
}
