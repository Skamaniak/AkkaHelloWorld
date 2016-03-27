package cz.jskrabal.util;

/**
 * Created by Jan Skrabal skrabalja@gmail.com
 */
public class ActorUtils {
    public static <T> T inferType(Object o) {
        @SuppressWarnings("unchecked")
        T val = (T) o;
        return val;
    }
}
