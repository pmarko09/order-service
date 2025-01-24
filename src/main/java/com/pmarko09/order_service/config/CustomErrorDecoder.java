package com.pmarko09.order_service.config;

import com.pmarko09.order_service.exception.cart.CartNotFoundException;
import feign.FeignException;
import feign.Response;
import feign.RetryableException;
import feign.codec.ErrorDecoder;
import org.apache.coyote.BadRequestException;

public class CustomErrorDecoder implements ErrorDecoder {

    @Override
    public Exception decode(String methodKey, Response response) {
        FeignException exception = feign.FeignException.errorStatus(methodKey, response);
        switch (response.status()) {
            case 404:
                return new CartNotFoundException("Cart not found");
            case 400:
                return new BadRequestException("Invalid request to product service");
            case 500, 503:
                return new RetryableException(
                        response.status(),
                        exception.getMessage(),
                        response.request().httpMethod(),
                        0L,
                        response.request());
            default:
                return new RuntimeException("Error communicating with product service");
        }
    }
}