package com.company.paymentservice.service;

import com.company.paymentservice.entity.Payment;
import com.company.paymentservice.repository.PaymentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;

    public PaymentService(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }

    public Payment getPaymentById(Long id) {
        return paymentRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Payment not found with id: " + id));
    }

    public Payment createPayment(Payment payment) {
        return paymentRepository.save(payment);
    }

    public Payment updatePayment(Long id, Payment details) {
        Payment payment = getPaymentById(id);

        payment.setOrderId(details.getOrderId());
        payment.setAmount(details.getAmount());
        payment.setPaymentMethod(details.getPaymentMethod());
        payment.setStatus(details.getStatus());

        return paymentRepository.save(payment);
    }

    public void deletePayment(Long id) {
        Payment payment = getPaymentById(id);
        paymentRepository.delete(payment);
    }
}
