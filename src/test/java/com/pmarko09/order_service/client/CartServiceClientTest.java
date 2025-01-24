package com.pmarko09.order_service.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.pmarko09.order_service.model.dto.CartInfoDto;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import static com.github.tomakehurst.wiremock.client.WireMock.getRequestedFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
@AutoConfigureWireMock(port = 8091)
public class CartServiceClientTest {

    @Autowired
    private CartServiceClient cartServiceClient;

    @Autowired
    private WireMockServer wireMockServer;

    @Autowired
    ObjectMapper objectMapper;

    @AfterEach
    void tearDown() {
        wireMockServer.resetAll();
    }

    @Test
    void getCartById_CorrectId_CarInfoReturn() throws JsonProcessingException {
        log.info("Start test: getCartById_correctId_CarInfoReturn");
        CartInfoDto cartInfoDto = new CartInfoDto(5L, 999.9);

        wireMockServer.stubFor(WireMock.get("/cart/5")
                .willReturn(WireMock.aResponse()
                        .withStatus(200)
                        .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .withBody(objectMapper.writeValueAsString(cartInfoDto))));

        CartInfoDto result = cartServiceClient.getCartById(5L);

        assertNotNull(result);
        assertEquals(5L, result.getCartId());
        assertEquals(999.9, result.getTotalCartPrice());

        wireMockServer.verify(1, getRequestedFor(urlEqualTo("/cart/5")));
        log.info("Finish test: getCartById_correctId_CarInfoReturn");
    }
}