package com.example.temporal.workflow;

import com.example.temporal.activity.PaymentActivity;
import io.temporal.activity.ActivityOptions;
import io.temporal.workflow.Workflow;

import java.time.Duration;

public class OrderWorkflowImpl implements OrderWorkflow {

    private boolean paymentConfirmed = false;

    private final PaymentActivity paymentActivity =
            Workflow.newActivityStub(
                    PaymentActivity.class,
                    ActivityOptions.newBuilder()
                            .setStartToCloseTimeout(Duration.ofSeconds(10))
                            .build()
            );

    @Override
    public void processOrder(String orderId) {

        paymentActivity.charge(orderId);

        // Durable timer (does NOT block a thread)
        Workflow.sleep(Duration.ofSeconds(30));

        Workflow.await(() -> paymentConfirmed);

        paymentActivity.confirm(orderId);
    }

    @Override
    public void paymentProcessed() {
        paymentConfirmed = true;
    }
}