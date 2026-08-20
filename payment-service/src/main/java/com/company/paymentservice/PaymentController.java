package com.company.paymentservice;

import com.company.paymentservice.dto.CreatePaymentRequest;
import com.company.paymentservice.entity.Payment;
import com.company.paymentservice.service.PaymentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @GetMapping("/health")
    public Map<String, String> health() {
        return Map.of(
                "service", "payment-service",
                "status", "UP"
        );
    }

    @GetMapping
    public ResponseEntity<List<Payment>> getAllPayments() {
        return ResponseEntity.ok(paymentService.getAllPayments());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Payment> getPaymentById(@PathVariable Long id) {
        return ResponseEntity.ok(paymentService.getPaymentById(id));
    }

    @PostMapping
    public ResponseEntity<Payment> createPayment(
            @Valid @RequestBody CreatePaymentRequest request) {

        Payment payment = new Payment();
        payment.setOrderId(request.getOrderId());
        payment.setAmount(request.getAmount());
        payment.setPaymentMethod(request.getPaymentMethod());

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(paymentService.createPayment(payment));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Payment> updatePayment(
            @PathVariable Long id,
            @Valid @RequestBody CreatePaymentRequest request) {

        Payment details = new Payment();
        details.setOrderId(request.getOrderId());
        details.setAmount(request.getAmount());
        details.setPaymentMethod(request.getPaymentMethod());

        return ResponseEntity.ok(
                paymentService.updatePayment(id, details)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> deletePayment(
            @PathVariable Long id) {

        paymentService.deletePayment(id);

        return ResponseEntity.ok(
                Map.of(
                        "message", "Payment deleted successfully",
                        "id", String.valueOf(id)
                )
        );
    }
}
