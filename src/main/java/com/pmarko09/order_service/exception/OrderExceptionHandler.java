package com.pmarko09.order_service.exception;

import com.pmarko09.order_service.exception.cart.CartNotFoundException;
import com.pmarko09.order_service.exception.cart.CartServiceUnavailableException;
import com.pmarko09.order_service.exception.cart.EmptyCartException;
import com.pmarko09.order_service.exception.client.ClientNotFoundException;
import com.pmarko09.order_service.exception.client.IllegalClientNameException;
import com.pmarko09.order_service.exception.invoice.InvoiceNotFoundException;
import com.pmarko09.order_service.exception.order.OrderAlreadyWithInvoiceException;
import com.pmarko09.order_service.exception.order.OrderNotFoundException;
import com.pmarko09.order_service.model.dto.ErrorMessageDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@Slf4j
@ControllerAdvice
public class OrderExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(CartNotFoundException.class)
    protected ResponseEntity<ErrorMessageDto> handleCartNotFoundException(CartNotFoundException ex) {
        ErrorMessageDto bodyResponse = new ErrorMessageDto(ex.getMessage(), LocalDateTime.now(), HttpStatus.NOT_FOUND);
        log.error("Error occurred: {}", ex.getMessage());
        return new ResponseEntity<>(bodyResponse, new HttpHeaders(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(CartServiceUnavailableException.class)
    protected ResponseEntity<ErrorMessageDto> handleCartServiceUnavailableException(CartServiceUnavailableException ex) {
        ErrorMessageDto bodyResponse = new ErrorMessageDto(ex.getMessage(), LocalDateTime.now(), HttpStatus.SERVICE_UNAVAILABLE);
        log.error("Error occurred: {}", ex.getMessage());
        return new ResponseEntity<>(bodyResponse, new HttpHeaders(), HttpStatus.SERVICE_UNAVAILABLE);
    }

    @ExceptionHandler(ClientNotFoundException.class)
    protected ResponseEntity<ErrorMessageDto> handleClientNotFoundException(ClientNotFoundException ex) {
        ErrorMessageDto bodyResponse = new ErrorMessageDto(ex.getMessage(), LocalDateTime.now(), HttpStatus.NOT_FOUND);
        log.error("Error occurred: {}", ex.getMessage());
        return new ResponseEntity<>(bodyResponse, new HttpHeaders(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(IllegalClientNameException.class)
    protected ResponseEntity<ErrorMessageDto> handleIllegalClientNameException(IllegalClientNameException ex) {
        ErrorMessageDto bodyResponse = new ErrorMessageDto(ex.getMessage(), LocalDateTime.now(), HttpStatus.BAD_REQUEST);
        log.error("Error occurred: {}", ex.getMessage());
        return new ResponseEntity<>(bodyResponse, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvoiceNotFoundException.class)
    protected ResponseEntity<ErrorMessageDto> handleInvoiceNotFoundException(InvoiceNotFoundException ex) {
        ErrorMessageDto bodyResponse = new ErrorMessageDto(ex.getMessage(), LocalDateTime.now(), HttpStatus.NOT_FOUND);
        log.error("Error occurred: {}", ex.getMessage());
        return new ResponseEntity<>(bodyResponse, new HttpHeaders(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(OrderNotFoundException.class)
    protected ResponseEntity<ErrorMessageDto> handleOrderNotFoundException(OrderNotFoundException ex) {
        ErrorMessageDto bodyResponse = new ErrorMessageDto(ex.getMessage(), LocalDateTime.now(), HttpStatus.NOT_FOUND);
        log.error("Error occurred: {}", ex.getMessage());
        return new ResponseEntity<>(bodyResponse, new HttpHeaders(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(OrderAlreadyWithInvoiceException.class)
    protected ResponseEntity<ErrorMessageDto> handleOrderAlreadyWithInvoiceException(OrderAlreadyWithInvoiceException ex) {
        ErrorMessageDto bodyResponse = new ErrorMessageDto(ex.getMessage(), LocalDateTime.now(), HttpStatus.BAD_REQUEST);
        log.error("Error occurred: {}", ex.getMessage());
        return new ResponseEntity<>(bodyResponse, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EmptyCartException.class)
    protected ResponseEntity<ErrorMessageDto> handleEmptyCartException(EmptyCartException ex) {
        ErrorMessageDto bodyResponse = new ErrorMessageDto(ex.getMessage(), LocalDateTime.now(), HttpStatus.BAD_REQUEST);
        log.error("Error occurred: {}", ex.getMessage());
        return new ResponseEntity<>(bodyResponse, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }
}