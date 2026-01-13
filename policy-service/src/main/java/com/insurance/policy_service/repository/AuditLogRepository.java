/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.insurance.policy_service.entity.AuditLog
 *  com.insurance.policy_service.repository.AuditLogRepository
 *  org.springframework.data.jpa.repository.JpaRepository
 */
package com.insurance.policy_service.repository;

import com.insurance.policy_service.entity.AuditLog;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuditLogRepository
extends JpaRepository<AuditLog, UUID> {
}

