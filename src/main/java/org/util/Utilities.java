package org.util;

import java.lang.reflect.Field;
import java.nio.file.StandardOpenOption;
import java.util.*;
import javax.script.*;

public class Utilities {
    private static final Map<Class<?>, Class<?>> WRAPPER_TYPE_MAP;
    static {
        WRAPPER_TYPE_MAP = new HashMap<Class<?>, Class<?>>(16);
        WRAPPER_TYPE_MAP.put(Integer.class, int.class);
        WRAPPER_TYPE_MAP.put(Byte.class, byte.class);
        WRAPPER_TYPE_MAP.put(Character.class, char.class);
        WRAPPER_TYPE_MAP.put(Boolean.class, boolean.class);
        WRAPPER_TYPE_MAP.put(Double.class, double.class);
        WRAPPER_TYPE_MAP.put(Float.class, float.class);
        WRAPPER_TYPE_MAP.put(Long.class, long.class);
        WRAPPER_TYPE_MAP.put(Short.class, short.class);
        WRAPPER_TYPE_MAP.put(Void.class, void.class);
        WRAPPER_TYPE_MAP.put(UUID.class, UUID.class);

    }
    public static boolean isPrimitiveType(Object source) {
        return WRAPPER_TYPE_MAP.containsKey(source.getClass());
    }
    public static String toJson(Object obj) throws Exception {
        StringBuilder json = new StringBuilder();
         toJson(obj, json);
         return json.toString();
    }
    private static void toJson(Object obj, StringBuilder json) throws Exception {
        if (obj != null) {
            if (isPrimitiveType(obj) || (obj instanceof String)) {
                if (obj instanceof String || obj instanceof UUID) {
                    json.append("\"").append(obj).append("\"");
                    return;
                }
                json.append(obj).append("\n");
                return;
            }
            if (Collection.class.isAssignableFrom(obj.getClass())) {
                List<Object> list = (List<Object>) obj;
                json.append("[").append("\n");
                for(Object o : list) {
                    toJson(o, json);
                }
                json.append("]").append("\n");
                return;
            }
            json.append("{").append("\n");
                Field[] fields = getFields(obj);
                int total = fields.length;
                for(int i = 0; i < total; i++) {
                    Field field = fields[i];
                    json.append("\"").append(field.getName()).append("\"").append(": ");
                    field.setAccessible(true);
                    toJson(field.get(obj), json);
                    if (i < total - 1) {
                        json.append(",");
                    }
                }
            json.append("}").append("\n");
        }
    }
    public static <T> T fromJson(String json, Class<T> fromClass) throws Exception {
        T obj = fromClass.newInstance();
        fromJson(json, obj);
        return obj;
    }
    private static void fromJson(String json, Object target) throws Exception {
        Map<String, String> properties = toMap(json);
        Field[] fields = getFields(target);
        for(Field field : fields) {
            field.setAccessible(true);
            String value = properties.get(field.getName());
            if (value.startsWith("{")) {
                Object prop = field.getType().newInstance();
                fromJson(value, prop);
                field.set(target, prop);
                continue;
            }
            field.set(target, field.getType().cast(value));
        }
    }
    private static Map<String, String> toMap(String json) {
        json = json.substring(1, json.length() - 1).replace("\n", "").replace("\"", "");
        String[] keyValuePairs = json.split(",");
        Map<String, String> properties = new HashMap<>();

        for(String pair: keyValuePairs) {
            String[] entry = pair.split(":");
            properties.put(entry[0].trim(), entry[1].trim());
        }
        return properties;
    }
    private static Field[] getFields(Object obj) {
        List<Field> totalFields = new ArrayList<>();
        if (obj.getClass().getSuperclass() != null) {
            Collections.addAll(totalFields, obj.getClass().getSuperclass().getDeclaredFields());
        }
        Collections.addAll(totalFields, obj.getClass().getDeclaredFields());
        return (Field[]) totalFields.toArray();
    }
}
