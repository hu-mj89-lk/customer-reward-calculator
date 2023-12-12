package com.example.customer_reward_calculator.service;

import com.example.customer_reward_calculator.dto.TransactionDTO;
import com.example.customer_reward_calculator.entity.Customer;
import com.example.customer_reward_calculator.entity.Transaction;
import com.example.customer_reward_calculator.repository.CustomerRepository;
import com.example.customer_reward_calculator.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.NoSuchElementException;
import java.util.Optional;

import static com.example.customer_reward_calculator.util.ApplicationUtil.calculatePoints;
import static java.time.ZoneOffset.UTC;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final CustomerRepository customerRepository;

    public TransactionDTO getTransaction(TransactionDTO transactionDTO) {
        Optional<Transaction> optTransaction = transactionRepository.findById(transactionDTO.getId());
        if (optTransaction.isEmpty())
            throw new NoSuchElementException("transaction does not exist");

        Transaction transaction = optTransaction.get();

        transactionDTO.setCustomerId(transaction.getCustomer().getId());
        transactionDTO.setAmount(transaction.getAmount());
        transactionDTO.setDateTime(transaction.getDateTime());
        transactionDTO.setPoints(calculatePoints(transaction.getAmount()));

        return transactionDTO;
    }

    public TransactionDTO createTransaction(TransactionDTO transactionDTO) {
        if (!customerRepository.existsById(transactionDTO.getCustomerId()))
            throw new NoSuchElementException("customer does not exist");

        Customer customer = new Customer();
        customer.setId(transactionDTO.getCustomerId());

        Transaction transaction = new Transaction();
        transaction.setCustomer(customer);
        transaction.setAmount(transactionDTO.getAmount());
        transaction.setDateTime(Instant.now().atOffset(UTC));

        transaction = transactionRepository.save(transaction);

        transactionDTO.setId(transaction.getId());
        transactionDTO.setDateTime(transaction.getDateTime());
        transactionDTO.setPoints(calculatePoints(transaction.getAmount()));

        return transactionDTO;
    }

    public void updateTransaction(TransactionDTO transactionDTO) {
        Optional<Transaction> optTransaction = transactionRepository.findById(transactionDTO.getId());
        if (optTransaction.isEmpty())
            throw new NoSuchElementException("transaction does not exist");

        Transaction transaction = optTransaction.get();

        // update customer
        if (transactionDTO.getCustomerId() != null) {
            Optional<Customer> optCustomer = customerRepository.findById(transactionDTO.getCustomerId());
            if (optCustomer.isEmpty())
                throw new NoSuchElementException("customer does not exist");

            Customer customer = new Customer();
            customer.setId(transactionDTO.getCustomerId());

            transaction.setCustomer(customer);
        }
        // update amount
        if (transactionDTO.getAmount() != null) {
            transaction.setAmount(transactionDTO.getAmount());
        }
        // update date-time
        transaction.setDateTime(Instant.now().atOffset(UTC));

        transactionRepository.save(transaction);
    }
}
