/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.insurance.policy_service.entity.Policy
 *  com.insurance.policy_service.eventModel.PolicyEvent
 *  com.insurance.policy_service.eventModel.PolicyEventType
 */
package com.insurance.policy_service.eventModel;

import com.insurance.policy_service.entity.Policy;
import com.insurance.policy_service.eventModel.PolicyEventType;
import java.time.LocalDateTime;
import java.util.UUID;

public class PolicyEvent {
    private PolicyEventType eventType;
    private UUID policyId;
    private String policyNumber;
    private LocalDateTime occurredAt;

    public PolicyEvent(PolicyEventType eventType, Policy policy) {
        this.eventType = eventType;
        this.policyId = policy.getId();
        this.policyNumber = policy.getPolicyNumber();
        this.occurredAt = LocalDateTime.now();
    }

    public PolicyEventType getEventType() {
        return this.eventType;
    }

    public void setEventType(PolicyEventType eventType) {
        this.eventType = eventType;
    }

    public UUID getPolicyId() {
        return this.policyId;
    }

    public void setPolicyId(UUID policyId) {
        this.policyId = policyId;
    }

    public String getPolicyNumber() {
        return this.policyNumber;
    }

    public void setPolicyNumber(String policyNumber) {
        this.policyNumber = policyNumber;
    }

    public LocalDateTime getOccurredAt() {
        return this.occurredAt;
    }

    public void setOccurredAt(LocalDateTime occurredAt) {
        this.occurredAt = occurredAt;
    }
}

