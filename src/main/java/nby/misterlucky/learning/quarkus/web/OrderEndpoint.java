package nby.misterlucky.learning.quarkus.web;

import nby.misterlucky.learning.quarkus.domain.OrderStatus;
import nby.misterlucky.learning.quarkus.domain.entity.OrderStatusException;
import nby.misterlucky.learning.quarkus.repository.CustomerRepository;
import nby.misterlucky.learning.quarkus.repository.OrderRepository;
import nby.misterlucky.learning.quarkus.domain.entity.Customer;
import nby.misterlucky.learning.quarkus.domain.entity.Order;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.Collections;
import java.util.List;


@Path("orders")
@ApplicationScoped
@Produces("application/json")
@Consumes("application/json")
public class OrderEndpoint {

    @Inject
    OrderRepository orderRepository;
    @Inject
    CustomerRepository customerRepository;

    @GET
    public List<Order> getAll(@QueryParam("customerId") Long customerId) {
        return orderRepository.findAll(customerId);
    }

    @GET
    public List<Order> getByStatus(@QueryParam("status") Integer status) {
        try {
            return orderRepository.findByStatus(OrderStatus.byCode(status));
        } catch (OrderStatusException ex) {
            ex.printStackTrace();
            return Collections.emptyList();
        }
    }

    @POST
    @Path("/{customer}")
    public Response create(Order order, @PathParam("customer") Long customerId) {
        Customer c = customerRepository.findCustomerById(customerId);
        orderRepository.createOrder(order, c);
        return Response.status(201).build();
    }

    @PUT
    public Response update(Order order) {
        orderRepository.updateOrder(order);
        return Response.status(204).build();
    }

    @DELETE
    @Path("/{order}")
    public Response delete(@PathParam("order") Long orderId) {
        orderRepository.deleteOrder(orderId);
        return Response.status(204).build();
    }

}