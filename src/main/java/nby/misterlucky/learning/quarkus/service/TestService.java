package nby.misterlucky.learning.quarkus.service;

import nby.misterlucky.learning.quarkus.domain.TestResponseDTO;
import org.eclipse.microprofile.faulttolerance.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;

@ApplicationScoped
public class TestService {

    private static final Logger LOGGER = LoggerFactory.getLogger("TestService");

    private final AtomicInteger requestCounter = new AtomicInteger(0);

    @Timeout(1000)
    @Fallback(fallbackMethod = "failureResponse")
    @Retry(delay = 100, maxRetries = 2)
    public TestResponseDTO timeoutToleranceResponse(String text) {
        randomSleep();
        return new TestResponseDTO("Success response for " + text, true);
    }

    @Fallback(fallbackMethod = "failureResponse")
    @Retry(retryOn = CustomExecutionException.class, maxRetries = 2)
    public TestResponseDTO faultToleranceResponse(String text) {
        if (Math.random() > 0.3) throw new CustomExecutionException();
        return new TestResponseDTO("Success response for " + text, true);
    }

    public TestResponseDTO failureResponse(String text) {
        return new TestResponseDTO("Timeout occurred for" + text, false);
    }

    private void randomSleep() {
        try {
            Thread.sleep(new Random().nextInt(10000));
        } catch (java.lang.InterruptedException e) {
            e.printStackTrace();
        }

    }

    @Asynchronous
    @Bulkhead(value = 1, waitingTaskQueue = 1)
    /* allows just 1 simultaneously performing request and 1 in queue
     * if exceeds throws org.eclipse.microprofile.faulttolerance.exceptions.BulkheadException
     * */
    public Future<TestResponseDTO> getNextResult(String textPassed) {
        randomSleep();
        String result = "result for " + textPassed + " : " + String.valueOf(this.requestCounter.getAndIncrement());
        LOGGER.info(result);
        return CompletableFuture.completedFuture(new TestResponseDTO(result, true));
    }

}
