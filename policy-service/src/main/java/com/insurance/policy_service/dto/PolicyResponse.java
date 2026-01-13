/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.insurance.policy_service.dto.PolicyResponse
 *  com.insurance.policy_service.entity.Policy
 *  com.insurance.policy_service.entity.PolicyStatus
 */
package com.insurance.policy_service.dto;

import com.insurance.policy_service.entity.Policy;
import com.insurance.policy_service.entity.PolicyStatus;
import java.math.BigDecimal;
import java.util.UUID;

public class PolicyResponse {
    private UUID policyId;
    private String policyNumber;
    private String policyType;
    private BigDecimal premiumAmount;
    private PolicyStatus status;

    public static PolicyResponse fromEntity(Policy policy) {
        PolicyResponse response = new PolicyResponse();
        response.policyId = policy.getId();
        response.policyNumber = policy.getPolicyNumber();
        response.policyType = policy.getPolicyType();
        response.premiumAmount = policy.getPremiumAmount();
        response.status = policy.getStatus();
        return response;
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

    public String getPolicyType() {
        return this.policyType;
    }

    public void setPolicyType(String policyType) {
        this.policyType = policyType;
    }

    public BigDecimal getPremiumAmount() {
        return this.premiumAmount;
    }

    public void setPremiumAmount(BigDecimal premiumAmount) {
        this.premiumAmount = premiumAmount;
    }

    public PolicyStatus getStatus() {
        return this.status;
    }

    public void setStatus(PolicyStatus status) {
        this.status = status;
    }
}

