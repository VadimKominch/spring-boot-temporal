package com.example.temporal.activity;

import org.springframework.stereotype.Component;

@Component
public class PaymentActivityImpl implements PaymentActivity {

    @Override
    public void charge(String orderId) {
        System.out.println("Charging order: " + orderId);
    }

    @Override
    public void confirm(String orderId) {
        System.out.println("Confirming order: " + orderId);
    }
}