package com.example.customer_reward_calculator.documentation;

import com.example.customer_reward_calculator.api.customer.CreateCustomerRQ;
import com.example.customer_reward_calculator.api.customer.CustomerPointsRQ;
import com.example.customer_reward_calculator.api.customer.UpdateCustomerRQ;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.test.annotation.DirtiesContext.ClassMode.AFTER_CLASS;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DirtiesContext(classMode = AFTER_CLASS)
@SpringBootTest
@ExtendWith(RestDocumentationExtension.class)
class CustomerApiDocumentationTests {

    @Autowired
    private WebApplicationContext webApplicationContext;
    @Autowired
    private ObjectMapper objectMapper;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp(RestDocumentationContextProvider restDocumentation) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(documentationConfiguration(restDocumentation)
                        .operationPreprocessors()
                        .withRequestDefaults(prettyPrint())
                        .withResponseDefaults(prettyPrint()))
                .build();
    }

    @Test
    void createCustomerDocumentation() throws Exception {
        CreateCustomerRQ createCustomerRQ = new CreateCustomerRQ();
        createCustomerRQ.setName("John");

        this.mockMvc.perform(
                        post("/customer")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(createCustomerRQ)))
                .andExpect(status().isOk())
                .andDo(document("customer/create"));
    }

    @Test
    void updateCustomerDocumentation() throws Exception {
        UpdateCustomerRQ updateCustomerRQ = new UpdateCustomerRQ();
        updateCustomerRQ.setName("Suresh");

        this.mockMvc.perform(
                        put("/customer/1")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(updateCustomerRQ)))
                .andExpect(status().isOk())
                .andDo(document("customer/update"));
    }

    @Test
    void getCustomerDocumentation() throws Exception {
        this.mockMvc.perform(
                        get("/customer/1"))
                .andExpect(status().isOk())
                .andDo(document("customer/get"));
    }

    @Test
    void customerPointsDocumentation() throws Exception {
        CustomerPointsRQ customerPointsRQ = new CustomerPointsRQ();
        customerPointsRQ.setCustomerId(1L);
        customerPointsRQ.setYear(2023);
        customerPointsRQ.setMonths(List.of(4, 5, 6));

        this.mockMvc.perform(
                        post("/customer/points")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(customerPointsRQ)))
                .andExpect(status().isOk())
                .andDo(document("customer/points"));
    }

}
