package com.example.customer_reward_calculator.documentation;

import com.example.customer_reward_calculator.api.transaction.CreateTransactionRQ;
import com.example.customer_reward_calculator.api.transaction.UpdateTransactionRQ;
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

import java.math.BigDecimal;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.test.annotation.DirtiesContext.ClassMode.AFTER_CLASS;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DirtiesContext(classMode = AFTER_CLASS)
@SpringBootTest
@ExtendWith(RestDocumentationExtension.class)
class TransactionApiDocumentationTests {

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
    void createTransactionDocumentation() throws Exception {
        CreateTransactionRQ createTransactionRQ = new CreateTransactionRQ();
        createTransactionRQ.setCustomerId(1L);
        createTransactionRQ.setAmount(new BigDecimal("23.67"));

        this.mockMvc.perform(
                        post("/transaction")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(createTransactionRQ)))
                .andExpect(status().isOk())
                .andDo(document("transaction/create"));
    }

    @Test
    void updateTransactionDocumentation() throws Exception {
        UpdateTransactionRQ updateTransactionRQ = new UpdateTransactionRQ();
        updateTransactionRQ.setAmount(new BigDecimal("45"));

        this.mockMvc.perform(
                        put("/transaction/1")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(updateTransactionRQ)))
                .andExpect(status().isOk())
                .andDo(document("transaction/update"));
    }

    @Test
    void getTransactionDocumentation() throws Exception {
        this.mockMvc.perform(
                        get("/transaction/1"))
                .andExpect(status().isOk())
                .andDo(document("transaction/get"));
    }
}
