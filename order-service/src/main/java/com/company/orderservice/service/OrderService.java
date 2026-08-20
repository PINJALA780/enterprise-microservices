package com.company.orderservice.service;

import com.company.orderservice.entity.Order;
import com.company.orderservice.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Order getOrderById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Order not found with id: " + id));
    }

    public Order createOrder(Order order) {
        return orderRepository.save(order);
    }

    public Order updateOrder(Long id, Order details) {

        Order order = getOrderById(id);

        order.setUserId(details.getUserId());
        order.setProductId(details.getProductId());
        order.setQuantity(details.getQuantity());
        order.setTotalPrice(details.getTotalPrice());
        order.setStatus(details.getStatus());

        return orderRepository.save(order);
    }

    public void deleteOrder(Long id) {

        Order order = getOrderById(id);

        orderRepository.delete(order);
    }
}
