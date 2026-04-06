package com.example.demo.exception;

import java.time.LocalDateTime;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ErrorResponse {
	private String code;
    private String message;
    private Map<String, String> details;
    @Builder.Default
    private LocalDateTime timestamp = LocalDateTime.now();
}


