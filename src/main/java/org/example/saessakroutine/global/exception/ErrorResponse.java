package org.example.saessakroutine.global.exception;

public record ErrorResponse(
        String type,
        String message,
        int statusCode
) {
}
