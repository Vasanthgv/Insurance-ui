/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.insurance.policy_service.exception.BusinessException
 *  com.insurance.policy_service.exception.GlobalExceptionHandler
 *  org.springframework.http.ResponseEntity
 *  org.springframework.web.bind.annotation.ExceptionHandler
 *  org.springframework.web.bind.annotation.RestControllerAdvice
 */
package com.insurance.policy_service.exception;

import com.insurance.policy_service.exception.BusinessException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(value={BusinessException.class})
    public ResponseEntity<Map<String, Object>> handleBusinessException(BusinessException ex) {
        HashMap<String, Object> error = new HashMap<String, Object>();
        error.put("errorCode", ex.getErrorCode());
        error.put("message", ex.getMessage());
        error.put("timestamp", LocalDateTime.now());
        return ResponseEntity.badRequest().body(error);
    }
}

