package cz.jskrabal.concurrency.akka.message;

/**
 * Created by Jan Skrabal skrabalja@gmail.com
 */
public class Done {
    private static final Done INSTANCE = new Done();

    private Done() {
    }

    public static Done instance(){
        return INSTANCE;
    }
}
