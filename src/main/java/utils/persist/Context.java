package utils.persist;

import java.util.HashMap;
import java.util.Map;

public class Context {
    private static final ThreadLocal<Map<String, Object>> threadLocal = new ThreadLocal<>();

    private static Map<String, Object> getThread() {
        Map<String, Object> temp = threadLocal.get();
        if (temp == null) {
            temp = new HashMap<>();
            threadLocal.set(temp);
        }
        return temp;
    }

    public static Map<String, Object> asMap() {
        return getThread();
    }

    public static void put(String key, Object value) {
        getThread().put(key, value);
    }

    public static <T> T getValue(String key) {
        return (T) getThread().get(key);
    }

    public static <T> T remove(String key) {
        return (T) getThread().remove(key);
    }

    public static void clear() {
        getThread().clear();
    }

    public static boolean has(String key) {
        return getThread().containsKey(key);
    }
}
