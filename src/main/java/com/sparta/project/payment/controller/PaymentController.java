package com.sparta.project.payment.controller;

import com.sparta.project.payment.dto.PaymentResponseDto;
import com.sparta.project.payment.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/payments")
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping
    public PaymentResponseDto payAccess(@RequestParam String orderId){

        return paymentService.payAccess(orderId);
    }

    @PatchMapping("/{paymentId}/cancel")
    public void cancel(@PathVariable String paymentId){

        paymentService.cancel(paymentId);
    }
}
