package com.pmarko09.order_service.retry;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.pmarko09.order_service.model.dto.OrderDto;
import com.pmarko09.order_service.model.dto.OrderRequestDto;
import com.pmarko09.order_service.model.entity.Address;
import com.pmarko09.order_service.model.entity.Client;
import com.pmarko09.order_service.model.entity.OrderDelivery;
import com.pmarko09.order_service.model.entity.PaymentForm;
import com.pmarko09.order_service.repository.ClientRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.web.util.UriComponentsBuilder;

import static com.github.tomakehurst.wiremock.client.WireMock.getRequestedFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;

@Slf4j
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@AutoConfigureWireMock(port = 8091)
public class RetryTest {

    @Autowired
    WireMockServer wireMockServer;

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    ClientRepository clientRepository;

    @AfterEach
    public void tearDown() {
        wireMockServer.resetAll();
    }

    @Test
    void onceError500TriggerRetry() {
        log.info("Start test: onceError500TriggerRetry");
        Address address = new Address(1L, "WWA", "Krakowska", "12", "4",
                "44-111", "PL");
        Client client = new Client(1L, "Jan", "Kowal", "jk@", address);
        clientRepository.save(client);

        wireMockServer.stubFor(WireMock.get("/cart/1")
                .willReturn(WireMock.aResponse()
                        .withStatus(500)
                        .withHeader(HttpHeaders.RETRY_AFTER, "3")));

        OrderRequestDto orderRequestDto = new OrderRequestDto(1L, 1L, OrderDelivery.PACZKOMAT, PaymentForm.CASH);

        String url = UriComponentsBuilder.fromHttpUrl("http://localhost:8065")
                .path("/order")
                .toUriString();

        Assertions.assertThrows(Exception.class,
                () -> restTemplate.postForEntity(url, orderRequestDto, OrderDto.class));

        wireMockServer.verify(3, getRequestedFor(urlEqualTo("/cart/1")));
        log.info("Finish test: onceError500TriggerRetry");
    }
}