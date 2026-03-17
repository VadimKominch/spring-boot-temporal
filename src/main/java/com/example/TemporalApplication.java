package com.example;

import com.example.temporal.workflow.OrderWorkflow;
import io.temporal.client.WorkflowClient;
import io.temporal.client.WorkflowOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.UUID;

@SpringBootApplication
public class TemporalApplication implements CommandLineRunner {

    private final WorkflowClient client;

    public TemporalApplication(WorkflowClient client) {
        this.client = client;
    }

    public static void main(String[] args) {
        SpringApplication.run(TemporalApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        String id = UUID.randomUUID().toString();
        OrderWorkflow workflow = client.newWorkflowStub(
                OrderWorkflow.class,
                WorkflowOptions.newBuilder()
                        .setTaskQueue("order-queue")
                        .setWorkflowId("order-" + id) // idempotency
                        .build()
        );

        WorkflowClient.start(workflow::processOrder, id);

        System.out.println("Started workflow for order " + id);
    }
}