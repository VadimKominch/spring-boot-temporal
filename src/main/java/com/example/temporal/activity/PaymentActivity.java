package com.example.temporal.activity;

import io.temporal.activity.ActivityInterface;

@ActivityInterface
public interface PaymentActivity {

    void charge(String orderId);

    void confirm(String orderId);
}