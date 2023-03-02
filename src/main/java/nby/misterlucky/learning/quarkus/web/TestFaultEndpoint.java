package nby.misterlucky.learning.quarkus.web;

import nby.misterlucky.learning.quarkus.domain.TestResponseDTO;

import nby.misterlucky.learning.quarkus.service.TestService;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;

@Path("test")
@ApplicationScoped
@Produces("application/json")
public class TestFaultEndpoint {

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

}
