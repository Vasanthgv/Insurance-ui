/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.insurance.policy_service.entity.Policy
 *  com.insurance.policy_service.entity.PolicyStatus
 *  com.insurance.policy_service.repository.PolicyRepository
 *  org.springframework.data.domain.Page
 *  org.springframework.data.domain.Pageable
 *  org.springframework.data.jpa.repository.JpaRepository
 */
package com.insurance.policy_service.repository;

import com.insurance.policy_service.entity.Policy;
import com.insurance.policy_service.entity.PolicyStatus;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PolicyRepository
extends JpaRepository<Policy, UUID> {
    public Optional<Policy> findByPolicyNumber(String var1);

    public List<Policy> findByStatus(PolicyStatus var1);

    public Page<Policy> findByCreatedAt(LocalDateTime var1, Pageable var2);

    public Page<Policy> findByStatus(PolicyStatus var1, Pageable var2);

    public Page<Policy> findByCreatedAt(String var1, Pageable var2);
}

