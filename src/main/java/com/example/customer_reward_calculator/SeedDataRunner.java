package com.example.customer_reward_calculator;

import com.example.customer_reward_calculator.entity.Customer;
import com.example.customer_reward_calculator.entity.Transaction;
import com.example.customer_reward_calculator.repository.CustomerRepository;
import com.example.customer_reward_calculator.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;

import static java.time.ZoneOffset.UTC;

@Slf4j
@Component
@RequiredArgsConstructor
public class SeedDataRunner implements ApplicationListener<ApplicationReadyEvent> {

    private final CustomerRepository customerRepository;
    private final TransactionRepository transactionRepository;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        log.info("seed data :: started");
        Customer customer1 = createCustomer("John");

        createTransaction(customer1, new BigDecimal("23.67"), OffsetDateTime.of(LocalDateTime.of(2023, 4, 13, 18, 23), UTC));
        createTransaction(customer1, new BigDecimal("156.46"), OffsetDateTime.of(LocalDateTime.of(2023, 4, 25, 13, 34), UTC));
        createTransaction(customer1, new BigDecimal("27.50"), OffsetDateTime.of(LocalDateTime.of(2023, 4, 28, 12, 13), UTC));
        createTransaction(customer1, new BigDecimal("98.10"), OffsetDateTime.of(LocalDateTime.of(2023, 4, 4, 10, 43), UTC));

        createTransaction(customer1, new BigDecimal("71.90"), OffsetDateTime.of(LocalDateTime.of(2023, 5, 7, 9, 22), UTC));
        createTransaction(customer1, new BigDecimal("12.31"), OffsetDateTime.of(LocalDateTime.of(2023, 5, 23, 23, 45), UTC));
        createTransaction(customer1, new BigDecimal("439.31"), OffsetDateTime.of(LocalDateTime.of(2023, 5, 26, 17, 7), UTC));

        createTransaction(customer1, new BigDecimal("46.89"), OffsetDateTime.of(LocalDateTime.of(2023, 6, 1, 6, 32), UTC));
        createTransaction(customer1, new BigDecimal("59.33"), OffsetDateTime.of(LocalDateTime.of(2023, 6, 5, 14, 31), UTC));
        createTransaction(customer1, new BigDecimal("93.47"), OffsetDateTime.of(LocalDateTime.of(2023, 6, 22, 14, 38), UTC));
        createTransaction(customer1, new BigDecimal("53.98"), OffsetDateTime.of(LocalDateTime.of(2023, 6, 10, 16, 35), UTC));

        Customer customer2 = createCustomer("Jane");

        createTransaction(customer2, new BigDecimal("57.38"), OffsetDateTime.of(LocalDateTime.of(2023, 4, 13, 18, 23), UTC));
        createTransaction(customer2, new BigDecimal("479.33"), OffsetDateTime.of(LocalDateTime.of(2023, 4, 25, 13, 34), UTC));
        createTransaction(customer2, new BigDecimal("469.43"), OffsetDateTime.of(LocalDateTime.of(2023, 4, 28, 12, 13), UTC));
        createTransaction(customer2, new BigDecimal("39.82"), OffsetDateTime.of(LocalDateTime.of(2023, 4, 4, 10, 43), UTC));
        createTransaction(customer2, new BigDecimal("32.96"), OffsetDateTime.of(LocalDateTime.of(2023, 4, 7, 9, 22), UTC));
        createTransaction(customer2, new BigDecimal("83.70"), OffsetDateTime.of(LocalDateTime.of(2023, 4, 23, 23, 45), UTC));

        createTransaction(customer2, new BigDecimal("91.21"), OffsetDateTime.of(LocalDateTime.of(2023, 5, 26, 17, 7), UTC));

        createTransaction(customer2, new BigDecimal("894.26"), OffsetDateTime.of(LocalDateTime.of(2023, 6, 1, 6, 32), UTC));
        createTransaction(customer2, new BigDecimal("38.20"), OffsetDateTime.of(LocalDateTime.of(2023, 6, 5, 14, 31), UTC));
        createTransaction(customer2, new BigDecimal("26.54"), OffsetDateTime.of(LocalDateTime.of(2023, 6, 22, 14, 38), UTC));
        createTransaction(customer2, new BigDecimal("01.98"), OffsetDateTime.of(LocalDateTime.of(2023, 6, 10, 16, 35), UTC));

        log.info("seed data :: finished");
    }

    private Customer createCustomer(String name) {
        Customer customer = new Customer();
        customer.setName(name);

        return customerRepository.save(customer);
    }

    private void createTransaction(Customer customer,
                                   BigDecimal amount,
                                   OffsetDateTime dateTime) {
        Transaction transaction = new Transaction();
        transaction.setCustomer(customer);
        transaction.setAmount(amount);
        transaction.setDateTime(dateTime);

        transactionRepository.save(transaction);
    }

}
