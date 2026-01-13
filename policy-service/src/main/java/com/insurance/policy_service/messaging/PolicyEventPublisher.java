/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.insurance.policy_service.eventModel.PolicyEvent
 *  com.insurance.policy_service.messaging.PolicyEventPublisher
 *  org.springframework.kafka.core.KafkaTemplate
 *  org.springframework.stereotype.Component
 */
package com.insurance.policy_service.messaging;

import com.insurance.policy_service.eventModel.PolicyEvent;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class PolicyEventPublisher {
    private static final String TOPIC = "policy.events";
    private final KafkaTemplate<String, PolicyEvent> kafkaTemplate;

    public PolicyEventPublisher(KafkaTemplate<String, PolicyEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void publish(PolicyEvent event) {
        this.kafkaTemplate.send(TOPIC, event.getPolicyId().toString(), event);
    }
}

