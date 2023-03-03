package nby.misterlucky.learning.quarkus.web;

import nby.misterlucky.learning.quarkus.domain.TestResponseDTO;

import nby.misterlucky.learning.quarkus.service.TestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import java.util.concurrent.ExecutionException;

@Path("test")
@ApplicationScoped
@Produces("application/json")
public class TestFaultToleranceEndpoint {

    private static final Logger LOGGER = LoggerFactory.getLogger("TestFaultToleranceEndpoint");

    @Inject
    TestService testService;

    @GET
    @Path("/timeout/{path}")
    public TestResponseDTO testTimeoutTolerance(@PathParam("path") String path) {
        return testService.timeoutToleranceResponse(path);
    }

    @GET
    @Path("/fault/{path}")
    public TestResponseDTO testFaultTolerance(@PathParam("path") String path) {
        return testService.faultToleranceResponse(path);
    }

    @GET
    @Path("/async/{path}")
    public TestResponseDTO testAsynchronous(@PathParam("path") String path) throws ExecutionException, InterruptedException {
        LOGGER.info("request received: " + path);
        return testService.getNextResult(path).get();
    }

}
