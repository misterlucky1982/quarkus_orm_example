package nby.misterlucky.learning.quarkus.service;

import nby.misterlucky.learning.quarkus.domain.TestResponseDTO;
import org.eclipse.microprofile.faulttolerance.Fallback;
import org.eclipse.microprofile.faulttolerance.Retry;
import org.eclipse.microprofile.faulttolerance.Timeout;

import javax.enterprise.context.ApplicationScoped;
import java.util.Random;

@ApplicationScoped
public class TestService {

    @Timeout(1000)
    @Fallback(fallbackMethod = "failureResponse")
    @Retry(delay = 100, maxRetries = 2)
    public TestResponseDTO timeoutToleranceResponse(String text){
        randomSleep();
        return new TestResponseDTO("Success response for "+text, true);
    }

    @Fallback(fallbackMethod = "failureResponse")
    @Retry(retryOn = CustomExecutionException.class, maxRetries = 2)
    public TestResponseDTO faultToleranceResponse(String text){
        if(Math.random()>0.3)throw new CustomExecutionException();
        return new TestResponseDTO("Success response for "+text, true);
    }

    public TestResponseDTO failureResponse(String text){
        return new TestResponseDTO("Timeout occurred for"+text, false);
    }

    private void randomSleep() {
        try {
            Thread.sleep(new Random().nextInt(5000));
        } catch (java.lang.InterruptedException e) {
            e.printStackTrace();
        }

    }

}
