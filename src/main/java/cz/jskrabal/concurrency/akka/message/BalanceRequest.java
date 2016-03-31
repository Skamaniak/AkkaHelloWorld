package cz.jskrabal.concurrency.akka.message;

/**
 * Created by Jan Skrabal skrabalja@gmail.com
 */
public class BalanceRequest {
    private static final BalanceRequest INSTANCE = new BalanceRequest();

    private BalanceRequest() {
    }

    public static BalanceRequest instance(){
        return INSTANCE;
    }
}
