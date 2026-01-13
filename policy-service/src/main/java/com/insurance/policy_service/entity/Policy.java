/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.insurance.policy_service.entity.Policy
 *  com.insurance.policy_service.entity.PolicyCoverage
 *  com.insurance.policy_service.entity.PolicyStatus
 *  jakarta.persistence.CascadeType
 *  jakarta.persistence.Column
 *  jakarta.persistence.Entity
 *  jakarta.persistence.EnumType
 *  jakarta.persistence.Enumerated
 *  jakarta.persistence.Id
 *  jakarta.persistence.OneToMany
 *  jakarta.persistence.Table
 */
package com.insurance.policy_service.entity;

import com.insurance.policy_service.entity.PolicyCoverage;
import com.insurance.policy_service.entity.PolicyStatus;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name="policy")
public class Policy {
    @Id
    private UUID id;
    @Column(nullable=false, unique=true)
    private String policyNumber;
    @Column(nullable=false)
    private UUID customerId;
    @Enumerated(value=EnumType.STRING)
    @Column(nullable=false)
    private PolicyStatus status;
    @Column(nullable=false)
    private String policyType;
    @Column(nullable=false)
    private BigDecimal premiumAmount;
    private LocalDate startDate;
    private LocalDate endDate;
    @Column(nullable=false)
    private LocalDateTime createdAt;
    @Column(nullable=false)
    private LocalDateTime updatedAt;
    @OneToMany(mappedBy="policy", cascade={CascadeType.ALL})
    private List<PolicyCoverage> coverages = new ArrayList();

    public UUID getId() {
        return this.id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getPolicyNumber() {
        return this.policyNumber;
    }

    public void setPolicyNumber(String policyNumber) {
        this.policyNumber = policyNumber;
    }

    public UUID getCustomerId() {
        return this.customerId;
    }

    public void setCustomerId(UUID customerId) {
        this.customerId = customerId;
    }

    public PolicyStatus getStatus() {
        return this.status;
    }

    public void setStatus(PolicyStatus status) {
        this.status = status;
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

    public LocalDate getStartDate() {
        return this.startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return this.endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public LocalDateTime getCreatedAt() {
        return this.createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return this.updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public List<PolicyCoverage> getCoverages() {
        return this.coverages;
    }

    public void setCoverages(List<PolicyCoverage> coverages) {
        this.coverages = coverages;
    }
}

