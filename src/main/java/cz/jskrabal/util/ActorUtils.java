package cz.jskrabal.util;

import akka.actor.UntypedActor;

/**
 * Created by Jan Skrabal skrabalja@gmail.com
 */
public class ActorUtils {
    public static <T> T inferType(Object o) {
        @SuppressWarnings("unchecked")
        T val = (T) o;
        return val;
    }

    public static void busyWait() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            //Do nothing
        }
    }

    public static class PrintActor extends UntypedActor {
        @Override
        public void onReceive(Object message) throws Exception {
            System.out.println(message.toString());
        }
    }
}
