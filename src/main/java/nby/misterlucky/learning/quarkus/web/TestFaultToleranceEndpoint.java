package nby.misterlucky.learning.quarkus.web;

import nby.misterlucky.learning.quarkus.domain.TestResponseDTO;

import nby.misterlucky.learning.quarkus.service.TestService;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import java.util.concurrent.ExecutionException;

@Path("test")
@ApplicationScoped
@Produces("application/json")
@Tag(name = "OpenAPI Example", description = "Quarkus Fault Tolerance Example")
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

    @Operation(operationId = "all", description = "Just for example")
    @APIResponse(responseCode = "200", description = "Success response")
    @GET
    @Path("/success/{path}")
    public TestResponseDTO successResponse(@PathParam("path") String path) {
        return new TestResponseDTO("Success response for " + path, true);
    }

}
