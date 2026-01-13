/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.insurance.policy_service.controller.PolicyController
 *  com.insurance.policy_service.dto.CreatePolicyRequest
 *  com.insurance.policy_service.dto.PolicyResponse
 *  com.insurance.policy_service.entity.Policy
 *  com.insurance.policy_service.entity.PolicyStatus
 *  com.insurance.policy_service.service.PolicyService
 *  jakarta.validation.Valid
 *  org.springframework.data.domain.Page
 *  org.springframework.data.domain.PageRequest
 *  org.springframework.data.domain.Pageable
 *  org.springframework.http.HttpStatus
 *  org.springframework.http.HttpStatusCode
 *  org.springframework.http.ResponseEntity
 *  org.springframework.security.access.prepost.PreAuthorize
 *  org.springframework.security.core.Authentication
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RequestParam
 *  org.springframework.web.bind.annotation.RestController
 */
package com.insurance.policy_service.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.insurance.policy_service.dto.CreatePolicyRequest;
import com.insurance.policy_service.dto.PolicyResponse;
import com.insurance.policy_service.entity.Policy;
import com.insurance.policy_service.entity.PolicyStatus;
import com.insurance.policy_service.service.PolicyService;

import jakarta.validation.Valid;
@CrossOrigin(
	    origins = "http://localhost:4200",
	    allowedHeaders = "*",
	    methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE}
	)
@RestController
@RequestMapping(value={"/api/policies"})
public class PolicyController {
    private final PolicyService policyService;

    public PolicyController(PolicyService policyService) {
        this.policyService = policyService;
    }

    @PostMapping
    @PreAuthorize(value="hasRole('AGENT')")
    public ResponseEntity<PolicyResponse> createPolicy(@Valid @RequestBody CreatePolicyRequest request) {
        Policy policy = this.policyService.createPolicy(request.getCustomerId(), request.getPolicyType(), request.getPremiumAmount());
        return ResponseEntity.status((HttpStatusCode)HttpStatus.CREATED).body(PolicyResponse.fromEntity((Policy)policy));
    }

    @GetMapping(value={"/{status}"})
    @PreAuthorize(value="hasRole('UNDERWRITER')")
    public ResponseEntity<List<PolicyResponse>> getSubmittedPolicies(@PathVariable PolicyStatus status) {
        List<PolicyResponse> responses = this.policyService.findByStatus(status).stream().map(PolicyResponse::fromEntity).toList();
        return ResponseEntity.ok(responses);
    }

    @PostMapping(value={"/{id}/submit"})
    @PreAuthorize(value="hasRole('AGENT')")
    public PolicyResponse submitPolicy(@PathVariable UUID id) {
        return PolicyResponse.fromEntity((Policy)this.policyService.submitPolicy(id));
    }

    @PostMapping(value={"/{id}/approve"})
    @PreAuthorize(value="hasRole('UNDERWRITER')")
    public PolicyResponse approvePolicy(@PathVariable UUID id, Authentication authentication) {
        return PolicyResponse.fromEntity((Policy)this.policyService.approvePolicy(id, authentication.getName()));
    }

    @PostMapping(value={"/{id}/reject"})
    @PreAuthorize(value="hasRole('UNDERWRITER')")
    public PolicyResponse rejectPolicy(@PathVariable UUID id, Authentication authentication) {
        return PolicyResponse.fromEntity((Policy)this.policyService.rejectPolicy(id, authentication.getName()));
    }

    @GetMapping
    @PreAuthorize(value="hasAnyRole('AGENT','UNDERWRITER')")
    public Page<PolicyResponse> listPolicies(@RequestParam int page, @RequestParam int size, @RequestParam(required=false) PolicyStatus status, Authentication authentication) {
        return this.policyService.listPolicies(authentication.getName(), status, (Pageable)PageRequest.of((int)page, (int)size));
    }
}

