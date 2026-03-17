package com.example.temporal.controller;

import com.example.temporal.workflow.OrderWorkflow;
import io.temporal.client.WorkflowClient;
import io.temporal.client.WorkflowOptions;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final WorkflowClient client;

    @PostMapping("/{id}")
    public String startWorkflow(@PathVariable String id) {

        OrderWorkflow workflow = client.newWorkflowStub(
                OrderWorkflow.class,
                WorkflowOptions.newBuilder()
                        .setTaskQueue("order-queue")
                        .setWorkflowId("order-" + id) // idempotency
                        .build()
        );

        WorkflowClient.start(workflow::processOrder, id);

        return "Started workflow for order " + id;
    }
}