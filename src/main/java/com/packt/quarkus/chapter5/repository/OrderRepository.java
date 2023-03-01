package com.packt.quarkus.chapter5.repository;

import com.packt.quarkus.chapter5.domain.OrderStatus;
import com.packt.quarkus.chapter5.domain.entity.Customer;
import com.packt.quarkus.chapter5.domain.entity.Order;
import io.quarkus.panache.common.Sort;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import javax.ws.rs.WebApplicationException;
import java.util.List;


@ApplicationScoped
public class OrderRepository {

    public List<Order> findAll(Long customerId) {

        List l = Order.list("customer.id", Sort.by("item"), customerId);
        return l;
    }

    public Order findOrderById(Long id) {

        Order order = Order.findById(id);

        if (order == null) {
            throw new WebApplicationException("Order with id of " + id + " does not exist.", 404);
        }
        return order;
    }

    @Transactional
    public void updateOrder(Order order) {

        Order orderToUpdate = findOrderById(order.id);
        orderToUpdate.item = order.item;
        orderToUpdate.price = order.price;
    }

    @Transactional
    public void createOrder(Order order, Customer c) {
        order.customer = c;
        order.status = OrderStatus.JUST_REQUESTED;
        order.persist();
    }

    @Transactional
    public void deleteOrder(Long orderId) {
        Order order = findOrderById(orderId);
        order.delete();
    }

    public List<Order> findByStatus(OrderStatus status) {
        return Order.list("status", status.getCode());
    }
}