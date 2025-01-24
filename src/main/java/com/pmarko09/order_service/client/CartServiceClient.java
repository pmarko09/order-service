package com.pmarko09.order_service.client;

import com.pmarko09.order_service.config.FeignConfig;
import com.pmarko09.order_service.fallback.CartServiceFallback;
import com.pmarko09.order_service.model.dto.CartInfoDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "cart-service",
        url = "${spring.cloud.openfeign.client.config.cartServiceClient.url}",
        configuration = FeignConfig.class,
        fallback = CartServiceFallback.class)
public interface CartServiceClient {

    @GetMapping("/cart/{cartId}")
    CartInfoDto getCartById(@PathVariable Long cartId);
}