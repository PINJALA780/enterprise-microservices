package com.company.orderservice;

import com.company.orderservice.dto.CreateOrderRequest;
import com.company.orderservice.entity.Order;
import com.company.orderservice.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/health")
    public Map<String, String> health() {
        return Map.of(
                "service", "order-service",
                "status", "UP"
        );
    }

    @GetMapping
    public ResponseEntity<List<Order>> getAllOrders() {
        return ResponseEntity.ok(orderService.getAllOrders());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable Long id) {
        return ResponseEntity.ok(orderService.getOrderById(id));
    }

    @PostMapping
    public ResponseEntity<Order> createOrder(
            @Valid @RequestBody CreateOrderRequest request) {

        Order order = new Order();

        order.setUserId(request.getUserId());
        order.setProductId(request.getProductId());
        order.setQuantity(request.getQuantity());
        order.setTotalPrice(request.getTotalPrice());

        Order savedOrder = orderService.createOrder(order);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(savedOrder);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Order> updateOrder(
            @PathVariable Long id,
            @Valid @RequestBody CreateOrderRequest request) {

        Order orderDetails = new Order();

        orderDetails.setUserId(request.getUserId());
        orderDetails.setProductId(request.getProductId());
        orderDetails.setQuantity(request.getQuantity());
        orderDetails.setTotalPrice(request.getTotalPrice());

        return ResponseEntity.ok(
                orderService.updateOrder(id, orderDetails)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> deleteOrder(
            @PathVariable Long id) {

        orderService.deleteOrder(id);

        return ResponseEntity.ok(
                Map.of(
                        "message", "Order deleted successfully",
                        "id", String.valueOf(id)
                )
        );
    }
}
