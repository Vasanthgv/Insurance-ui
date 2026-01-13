/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.insurance.policy_service.dto.PolicyResponse
 *  com.insurance.policy_service.entity.AuditLog
 *  com.insurance.policy_service.entity.Policy
 *  com.insurance.policy_service.entity.PolicyStatus
 *  com.insurance.policy_service.eventModel.PolicyEvent
 *  com.insurance.policy_service.eventModel.PolicyEventType
 *  com.insurance.policy_service.exception.BusinessException
 *  com.insurance.policy_service.exception.ErrorCode
 *  com.insurance.policy_service.messaging.PolicyEventPublisher
 *  com.insurance.policy_service.repository.AuditLogRepository
 *  com.insurance.policy_service.repository.PolicyRepository
 *  com.insurance.policy_service.service.PolicyService
 *  com.insurance.policy_service.service.PolicyServiceImpl
 *  jakarta.transaction.Transactional
 *  org.springframework.data.domain.Page
 *  org.springframework.data.domain.Pageable
 *  org.springframework.security.access.prepost.PreAuthorize
 *  org.springframework.stereotype.Service
 */
package com.insurance.policy_service.service;

import com.insurance.policy_service.dto.PolicyResponse;
import com.insurance.policy_service.entity.AuditLog;
import com.insurance.policy_service.entity.Policy;
import com.insurance.policy_service.entity.PolicyStatus;
import com.insurance.policy_service.eventModel.PolicyEvent;
import com.insurance.policy_service.eventModel.PolicyEventType;
import com.insurance.policy_service.exception.BusinessException;
import com.insurance.policy_service.exception.ErrorCode;
import com.insurance.policy_service.messaging.PolicyEventPublisher;
import com.insurance.policy_service.repository.AuditLogRepository;
import com.insurance.policy_service.repository.PolicyRepository;
import com.insurance.policy_service.service.PolicyService;
import jakarta.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class PolicyServiceImpl
implements PolicyService {
    private final PolicyRepository policyRepository;
    private final AuditLogRepository auditLogRepository;
    private final PolicyEventPublisher eventPublisher;

    public PolicyServiceImpl(PolicyRepository policyRepository, AuditLogRepository auditLogRepository, PolicyEventPublisher eventPublisher) {
        this.policyRepository = policyRepository;
        this.auditLogRepository = auditLogRepository;
        this.eventPublisher = eventPublisher;
    }

    public Policy createPolicy(UUID customerId, String policyType, BigDecimal premium) {
        Policy policy = new Policy();
        policy.setId(UUID.randomUUID());
        policy.setPolicyNumber(this.generatePolicyNumber());
        policy.setCustomerId(customerId);
        policy.setPolicyType(policyType);
        policy.setPremiumAmount(premium);
        policy.setStatus(PolicyStatus.DRAFT);
        policy.setCreatedAt(LocalDateTime.now());
        policy.setUpdatedAt(LocalDateTime.now());
        this.policyRepository.save(policy);
        this.audit("POLICY", policy.getId(), "CREATED");
        this.eventPublisher.publish(new PolicyEvent(PolicyEventType.POLICY_CREATED, policy));
        return policy;
    }

    public Policy submitPolicy(UUID policyId) {
        Policy policy = this.getPolicy(policyId);
        if (policy.getStatus() != PolicyStatus.DRAFT) {
            throw new BusinessException(ErrorCode.INVALID_POLICY_STATE.name(), "Only DRAFT policies can be submitted");
        }
        policy.setStatus(PolicyStatus.SUBMITTED);
        policy.setUpdatedAt(LocalDateTime.now());
        this.audit("POLICY", policyId, "SUBMITTED");
        this.eventPublisher.publish(new PolicyEvent(PolicyEventType.POLICY_SUBMITTED, policy));
        return policy;
    }

    public Policy approvePolicy(UUID policyId, String approvedBy) {
        Policy policy = this.getPolicy(policyId);
        if (policy.getStatus() != PolicyStatus.UNDER_REVIEW) {
            throw new BusinessException(ErrorCode.POLICY_NOT_APPROVABLE.name(), "Policy must be UNDER_REVIEW to approve");
        }
        policy.setStatus(PolicyStatus.ACTIVE);
        policy.setStartDate(LocalDate.now());
        policy.setUpdatedAt(LocalDateTime.now());
        this.audit("POLICY", policyId, "APPROVED by " + approvedBy);
        this.eventPublisher.publish(new PolicyEvent(PolicyEventType.POLICY_APPROVED, policy));
        return policy;
    }

    public Policy rejectPolicy(UUID policyId, String rejectedBy) {
        Policy policy = this.getPolicy(policyId);
        if (policy.getStatus() != PolicyStatus.UNDER_REVIEW) {
            throw new BusinessException(ErrorCode.INVALID_POLICY_STATE.name(), "Only UNDER_REVIEW policies can be rejected");
        }
        policy.setStatus(PolicyStatus.CANCELLED);
        policy.setUpdatedAt(LocalDateTime.now());
        this.audit("POLICY", policyId, "REJECTED by " + rejectedBy);
        this.eventPublisher.publish(new PolicyEvent(PolicyEventType.POLICY_REJECTED, policy));
        return policy;
    }

    private Policy getPolicy(UUID policyId) {
        return (Policy)this.policyRepository.findById(policyId).orElseThrow(() -> new BusinessException(ErrorCode.POLICY_NOT_FOUND.name(), "Policy not found"));
    }

    private void audit(String entity, UUID entityId, String action) {
        AuditLog log = new AuditLog();
        log.setId(UUID.randomUUID());
        log.setEntityName(entity);
        log.setEntityId(entityId);
        log.setAction(action);
        log.setPerformedAt(LocalDateTime.now());
        this.auditLogRepository.save(log);
    }

    private String generatePolicyNumber() {
        return "POL-" + System.currentTimeMillis();
    }

    public List<Policy> findByStatus(PolicyStatus status) {
        return this.policyRepository.findByStatus(status);
    }

    @PreAuthorize(value="hasRole('UNDERWRITER')")
    private boolean hasRole(String string) {
        return true;
    }

    public Page<PolicyResponse> listPolicies(String username, PolicyStatus status, Pageable pageable) {
        if (this.hasRole("UNDERWRITER")) {
            if (status != null) {
                return this.policyRepository.findByStatus(status, pageable).map(PolicyResponse::fromEntity);
            }
            return this.policyRepository.findAll(pageable).map(PolicyResponse::fromEntity);
        }
        return this.policyRepository.findByCreatedAt(username, pageable).map(PolicyResponse::fromEntity);
    }
}

