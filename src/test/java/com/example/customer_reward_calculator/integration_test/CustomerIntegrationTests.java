package com.example.customer_reward_calculator.integration_test;

import com.example.customer_reward_calculator.api.customer.*;
import com.example.customer_reward_calculator.api.error.ErrorRS;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.http.HttpMethod.PUT;
import static org.springframework.test.annotation.DirtiesContext.ClassMode.AFTER_CLASS;

@DirtiesContext(classMode = AFTER_CLASS)
@SpringBootTest(webEnvironment = RANDOM_PORT)
class CustomerIntegrationTests {

    @LocalServerPort
    private int port;
    @Autowired
    private TestRestTemplate restTemplate;

    private String getUrl() {
        return "http://localhost:" + port;
    }

    @Test
    void createCustomerSuccess() {
        CreateCustomerRQ createCustomerRQ = new CreateCustomerRQ();
        createCustomerRQ.setName("John");

        ResponseEntity<CreateCustomerRS> responseEntity =
                restTemplate.postForEntity(getUrl() + "/customer", createCustomerRQ, CreateCustomerRS.class);

        assertThat(responseEntity.getStatusCode().value()).isEqualTo(200);
        assertThat(responseEntity.getBody()).isNotNull();
        assertThat(responseEntity.getBody().getId()).isNotNull();
    }

    @Test
    void createCustomerFailure() {
        CreateCustomerRQ createCustomerRQ = new CreateCustomerRQ();

        ResponseEntity<ErrorRS> responseEntity =
                restTemplate.postForEntity(getUrl() + "/customer", createCustomerRQ, ErrorRS.class);

        assertThat(responseEntity.getStatusCode().value()).isEqualTo(400);
        assertThat(responseEntity.getBody()).isNotNull();
        assertThat(responseEntity.getBody().getStatus()).isEqualTo(400);
    }

    @Test
    void updateCustomerSuccess() {
        UpdateCustomerRQ updateCustomerRQ = new UpdateCustomerRQ();
        updateCustomerRQ.setName("Suresh");

        ResponseEntity<UpdateCustomerRS> responseEntity =
                restTemplate.exchange(getUrl() + "/customer/1", PUT, new HttpEntity<>(updateCustomerRQ), UpdateCustomerRS.class);

        assertThat(responseEntity.getStatusCode().value()).isEqualTo(200);
        assertThat(responseEntity.getBody()).isNotNull();
        assertThat(responseEntity.getBody().getStatus().booleanValue()).isTrue();
    }

    @Test
    void updateCustomerFailure() {
        UpdateCustomerRQ updateCustomerRQ = new UpdateCustomerRQ();
        updateCustomerRQ.setName("Suresh");

        ResponseEntity<ErrorRS> responseEntity =
                restTemplate.exchange(getUrl() + "/customer/10", PUT, new HttpEntity<>(updateCustomerRQ), ErrorRS.class);

        assertThat(responseEntity.getStatusCode().value()).isEqualTo(500);
        assertThat(responseEntity.getBody()).isNotNull();
        assertThat(responseEntity.getBody().getStatus()).isEqualTo(500);
    }

    @Test
    void getCustomerSuccess() {
        ResponseEntity<GetCustomerRS> responseEntity =
                restTemplate.getForEntity(getUrl() + "/customer/1", GetCustomerRS.class);

        assertThat(responseEntity.getStatusCode().value()).isEqualTo(200);
        assertThat(responseEntity.getBody()).isNotNull();
        assertThat(responseEntity.getBody().getName()).isNotBlank();
    }

    @Test
    void getCustomerFailure() {
        ResponseEntity<ErrorRS> responseEntity =
                restTemplate.getForEntity(getUrl() + "/customer/10", ErrorRS.class);

        assertThat(responseEntity.getStatusCode().value()).isEqualTo(500);
        assertThat(responseEntity.getBody()).isNotNull();
        assertThat(responseEntity.getBody().getStatus()).isEqualTo(500);
    }

    @Test
    void customerPointsSuccess() {
        CustomerPointsRQ customerPointsRQ = new CustomerPointsRQ();
        customerPointsRQ.setCustomerId(1L);
        customerPointsRQ.setYear(2023);
        customerPointsRQ.setMonths(List.of(4, 5, 6));

        ResponseEntity<CustomerPointsRS> responseEntity =
                restTemplate.postForEntity(getUrl() + "/customer/points", customerPointsRQ, CustomerPointsRS.class);

        assertThat(responseEntity.getStatusCode().value()).isEqualTo(200);
        assertThat(responseEntity.getBody()).isNotNull();
    }

}
