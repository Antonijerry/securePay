package com.securepay.common.api;

public record ApiErrorResponse(
        int status,
        String error,
        String message
) {
}