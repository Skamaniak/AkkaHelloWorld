package cz.jskrabal.concurrency.akka.message;

/**
 * Created by Jan Skrabal skrabalja@gmail.com
 */
public class DoneResponse {
    private static final DoneResponse INSTANCE = new DoneResponse();

    private DoneResponse() {
    }

    public static DoneResponse instance(){
        return INSTANCE;
    }
}
