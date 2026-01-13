/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.insurance.policy_service.dto.CreatePolicyRequest
 *  jakarta.validation.constraints.DecimalMin
 *  jakarta.validation.constraints.NotBlank
 *  jakarta.validation.constraints.NotNull
 */
package com.insurance.policy_service.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.UUID;

public class CreatePolicyRequest {
    @NotNull
    private UUID customerId;
    @NotBlank
    private String policyType;
    @NotNull
    @DecimalMin(value="0.0")
    private @NotNull @DecimalMin(value="0.0") BigDecimal premiumAmount;

    public UUID getCustomerId() {
        return this.customerId;
    }

    public void setCustomerId(UUID customerId) {
        this.customerId = customerId;
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
}

