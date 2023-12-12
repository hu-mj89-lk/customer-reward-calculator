package com.example.customer_reward_calculator.integration_test;

import com.example.customer_reward_calculator.api.error.ErrorRS;
import com.example.customer_reward_calculator.api.transaction.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.http.HttpMethod.PUT;
import static org.springframework.test.annotation.DirtiesContext.ClassMode.AFTER_CLASS;

@DirtiesContext(classMode = AFTER_CLASS)
@SpringBootTest(webEnvironment = RANDOM_PORT)
class TransactionIntegrationTests {

    @LocalServerPort
    private int port;
    @Autowired
    private TestRestTemplate restTemplate;

    private String getUrl() {
        return "http://localhost:" + port;
    }

    @Test
    void createTransactionSuccess() {
        CreateTransactionRQ createTransactionRQ = new CreateTransactionRQ();
        createTransactionRQ.setCustomerId(1L);
        createTransactionRQ.setAmount(new BigDecimal("23.67"));

        ResponseEntity<CreateTransactionRS> responseEntity =
                restTemplate.postForEntity(getUrl() + "/transaction", createTransactionRQ, CreateTransactionRS.class);

        assertThat(responseEntity.getStatusCode().value()).isEqualTo(200);
        assertThat(responseEntity.getBody()).isNotNull();
        assertThat(responseEntity.getBody().getId()).isNotNull();
    }

    @Test
    void createTransactionFailure() {
        CreateTransactionRQ createTransactionRQ = new CreateTransactionRQ();
        createTransactionRQ.setCustomerId(1L);

        ResponseEntity<ErrorRS> responseEntity =
                restTemplate.postForEntity(getUrl() + "/transaction", createTransactionRQ, ErrorRS.class);

        assertThat(responseEntity.getStatusCode().value()).isEqualTo(400);
        assertThat(responseEntity.getBody()).isNotNull();
        assertThat(responseEntity.getBody().getStatus()).isEqualTo(400);
    }

    @Test
    void updateTransactionSuccess() {
        UpdateTransactionRQ updateTransactionRQ = new UpdateTransactionRQ();
        updateTransactionRQ.setAmount(new BigDecimal("45"));

        ResponseEntity<UpdateTransactionRS> responseEntity =
                restTemplate.exchange(getUrl() + "/transaction/1", PUT, new HttpEntity<>(updateTransactionRQ), UpdateTransactionRS.class);

        assertThat(responseEntity.getStatusCode().value()).isEqualTo(200);
        assertThat(responseEntity.getBody()).isNotNull();
        assertThat(responseEntity.getBody().getStatus().booleanValue()).isTrue();
    }

    @Test
    void updateTransactionFailure() {
        UpdateTransactionRQ updateTransactionRQ = new UpdateTransactionRQ();
        updateTransactionRQ.setAmount(new BigDecimal("45"));

        ResponseEntity<ErrorRS> responseEntity =
                restTemplate.exchange(getUrl() + "/transaction/1000", PUT, new HttpEntity<>(updateTransactionRQ), ErrorRS.class);

        assertThat(responseEntity.getStatusCode().value()).isEqualTo(500);
        assertThat(responseEntity.getBody()).isNotNull();
        assertThat(responseEntity.getBody().getStatus()).isEqualTo(500);
    }

    @Test
    void getTransactionSuccess() {
        ResponseEntity<GetTransactionRS> responseEntity =
                restTemplate.getForEntity(getUrl() + "/transaction/1", GetTransactionRS.class);

        assertThat(responseEntity.getStatusCode().value()).isEqualTo(200);
        assertThat(responseEntity.getBody()).isNotNull();
        assertThat(responseEntity.getBody().getId()).isNotNull();
        assertThat(responseEntity.getBody().getCustomerId()).isNotNull();
        assertThat(responseEntity.getBody().getAmount()).isNotNull();
        assertThat(responseEntity.getBody().getDateTime()).isNotNull();
        assertThat(responseEntity.getBody().getPoints()).isNotNull();
    }

    @Test
    void getTransactionFailure() {
        ResponseEntity<ErrorRS> responseEntity =
                restTemplate.getForEntity(getUrl() + "/transaction/1000", ErrorRS.class);

        assertThat(responseEntity.getStatusCode().value()).isEqualTo(500);
        assertThat(responseEntity.getBody()).isNotNull();
        assertThat(responseEntity.getBody().getStatus()).isEqualTo(500);
    }

}
