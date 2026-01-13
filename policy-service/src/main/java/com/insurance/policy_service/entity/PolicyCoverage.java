/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.insurance.policy_service.entity.Policy
 *  com.insurance.policy_service.entity.PolicyCoverage
 *  jakarta.persistence.Entity
 *  jakarta.persistence.Id
 *  jakarta.persistence.JoinColumn
 *  jakarta.persistence.ManyToOne
 *  jakarta.persistence.Table
 */
package com.insurance.policy_service.entity;

import com.insurance.policy_service.entity.Policy;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name="policy_coverage")
public class PolicyCoverage {
    @Id
    private UUID id;
    @ManyToOne
    @JoinColumn(name="policy_id")
    private Policy policy;
    private String coverageType;
    private BigDecimal coverageAmount;
}

