package cz.jskrabal.concurrency;

import org.junit.After;
import org.junit.Before;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.fail;

/**
 * Created by Jan Skrabal skrabalja@gmail.com
 */
abstract class MultiThreadTest {
    private static final int THREAD_COUNT = 10;
    private static final long EXECUTION_TIMEOUT_SECONDS = 10L;

    ExecutorService executorService;

    @Before
    public void init() {
        executorService = Executors.newFixedThreadPool(THREAD_COUNT);
    }

    @After
    public void cleanUp() {
        executorService.shutdownNow();
    }

    void awaitCompletion() {
        executorService.shutdown();
        try {
            boolean completed = executorService.awaitTermination(EXECUTION_TIMEOUT_SECONDS, TimeUnit.SECONDS);

            if (!completed) {
                fail("Tasks were killed before completion!");
            }
        } catch (InterruptedException e) {
            //Do nothing
        }
    }
}
