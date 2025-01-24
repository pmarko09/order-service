package com.pmarko09.order_service.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ErrorMessageDto {

    private String message;
    private LocalDateTime time;
    private HttpStatus httpStatus;
}