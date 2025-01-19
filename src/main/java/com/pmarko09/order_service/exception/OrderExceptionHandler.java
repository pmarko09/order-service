package com.pmarko09.order_service.exception;

import com.pmarko09.order_service.exception.cart.CartNotFoundException;
import com.pmarko09.order_service.exception.cart.CartServiceUnavailableException;
import com.pmarko09.order_service.exception.client.ClientNotFoundException;
import com.pmarko09.order_service.exception.client.IllegalClientNameException;
import com.pmarko09.order_service.exception.invoice.InvoiceNotFoundException;
import com.pmarko09.order_service.exception.order.OrderAlreadyWithInvoiceException;
import com.pmarko09.order_service.exception.order.OrderNotFoundException;
import com.pmarko09.order_service.model.dto.ErrorInfoDto;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class OrderExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(CartNotFoundException.class)
    protected ResponseEntity<ErrorInfoDto> handleCartNotFoundException(CartNotFoundException ex) {
        ErrorInfoDto bodyResponse = new ErrorInfoDto(ex.getMessage(), LocalDateTime.now(), HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(bodyResponse, new HttpHeaders(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(CartServiceUnavailableException.class)
    protected ResponseEntity<ErrorInfoDto> handleCartServiceUnavailableException(CartServiceUnavailableException ex) {
        ErrorInfoDto bodyResponse = new ErrorInfoDto(ex.getMessage(), LocalDateTime.now(), HttpStatus.SERVICE_UNAVAILABLE);
        return new ResponseEntity<>(bodyResponse, new HttpHeaders(), HttpStatus.SERVICE_UNAVAILABLE);
    }

    @ExceptionHandler(ClientNotFoundException.class)
    protected ResponseEntity<ErrorInfoDto> handleClientNotFoundException(ClientNotFoundException ex) {
        ErrorInfoDto bodyResponse = new ErrorInfoDto(ex.getMessage(), LocalDateTime.now(), HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(bodyResponse, new HttpHeaders(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(IllegalClientNameException.class)
    protected ResponseEntity<ErrorInfoDto> handleIllegalClientNameException(IllegalClientNameException ex) {
        ErrorInfoDto bodyResponse = new ErrorInfoDto(ex.getMessage(), LocalDateTime.now(), HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(bodyResponse, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvoiceNotFoundException.class)
    protected ResponseEntity<ErrorInfoDto> handleInvoiceNotFoundException(InvoiceNotFoundException ex) {
        ErrorInfoDto bodyResponse = new ErrorInfoDto(ex.getMessage(), LocalDateTime.now(), HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(bodyResponse, new HttpHeaders(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(OrderNotFoundException.class)
    protected ResponseEntity<ErrorInfoDto> handleOrderNotFoundException(OrderNotFoundException ex) {
        ErrorInfoDto bodyResponse = new ErrorInfoDto(ex.getMessage(), LocalDateTime.now(), HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(bodyResponse, new HttpHeaders(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(OrderAlreadyWithInvoiceException.class)
    protected ResponseEntity<ErrorInfoDto> handleOrderAlreadyWithInvoiceException(OrderAlreadyWithInvoiceException ex) {
        ErrorInfoDto bodyResponse = new ErrorInfoDto(ex.getMessage(), LocalDateTime.now(), HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(bodyResponse, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }
}