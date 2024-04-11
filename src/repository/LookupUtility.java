package repository;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class LookupUtility {
    public static <T> T findByNameInMixedArray(String name, Object[] array, Function<T, String> accessName, Class<T> clazz) {
        for (Object o : array) {
            if (clazz.isInstance(o)) {
                T t = clazz.cast(o);
                if (accessName.apply(t).equals(name)) {
                    return t;
                }
            }
        }
        return null;
    }
    public static <T> T findByName(String name, T[] array, Function<T, String> accessName) {
        for (T t : array) {
            if (accessName.apply(t).equals(name)) {
                return t;
            }
        }
        return null;

    }
    public static <T> List<T> findAllInMixedArray(Object[] array, Class<T> clazz) {
        List<T> list = new ArrayList<>();
        for (Object o : array) {
            if (clazz.isInstance(o)) {
                list.add(clazz.cast(o));
            }
        }
        return list;
    }
    public LookupUtility() {
        throw new UnsupportedOperationException("Utility classes cannot be instantiated.");
    }
}
