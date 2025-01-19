package com.pmarko09.order_service.fallback;

import com.pmarko09.order_service.client.CartServiceClient;
import com.pmarko09.order_service.exception.cart.CartServiceUnavailableException;
import com.pmarko09.order_service.model.entity.CartInfo;
import org.springframework.stereotype.Component;

@Component
public class CartServiceFallback implements CartServiceClient {

    @Override
    public CartInfo getCartById(Long cartId) {
        throw new CartServiceUnavailableException("Fallback: unable to get the cart." +
                "Cart service not available at the moment.");
    }
}