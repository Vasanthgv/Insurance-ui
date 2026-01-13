/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.insurance.policy_service.dto.PolicyResponse
 *  com.insurance.policy_service.entity.Policy
 *  com.insurance.policy_service.entity.PolicyStatus
 *  com.insurance.policy_service.service.PolicyService
 *  org.springframework.data.domain.Page
 *  org.springframework.data.domain.Pageable
 */
package com.insurance.policy_service.service;

import com.insurance.policy_service.dto.PolicyResponse;
import com.insurance.policy_service.entity.Policy;
import com.insurance.policy_service.entity.PolicyStatus;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PolicyService {
    public Policy createPolicy(UUID var1, String var2, BigDecimal var3);

    public Policy submitPolicy(UUID var1);

    public Policy approvePolicy(UUID var1, String var2);

    public Policy rejectPolicy(UUID var1, String var2);

    public List<Policy> findByStatus(PolicyStatus var1);

    public Page<PolicyResponse> listPolicies(String var1, PolicyStatus var2, Pageable var3);
}

