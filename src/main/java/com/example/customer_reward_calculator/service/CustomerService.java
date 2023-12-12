package com.example.customer_reward_calculator.service;

import com.example.customer_reward_calculator.dto.CustomerDTO;
import com.example.customer_reward_calculator.dto.TransactionDTO;
import com.example.customer_reward_calculator.entity.Customer;
import com.example.customer_reward_calculator.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static com.example.customer_reward_calculator.util.ApplicationUtil.calculatePoints;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerDTO getCustomer(CustomerDTO customerDTO) {
        Optional<Customer> optCustomer = customerRepository.findById(customerDTO.getId());
        if (optCustomer.isEmpty())
            throw new NoSuchElementException("customer does not exist");

        Customer customer = optCustomer.get();

        List<TransactionDTO> transactionDTOs = customer.getTransactions().stream()
                .map(transaction -> TransactionDTO.builder()
                        .id(transaction.getId())
                        .amount(transaction.getAmount())
                        .dateTime(transaction.getDateTime())
                        .points(calculatePoints(transaction.getAmount()))
                        .build())
                .toList();

        customerDTO.setName(customer.getName());
        customerDTO.setTransactions(transactionDTOs);

        return customerDTO;
    }

    public CustomerDTO createCustomer(CustomerDTO customerDTO) {
        Customer customer = new Customer();
        customer.setName(customerDTO.getName());

        customer = customerRepository.save(customer);

        customerDTO.setId(customer.getId());

        return customerDTO;
    }

    public void updateCustomer(CustomerDTO customerDTO) {
        Optional<Customer> optCustomer = customerRepository.findById(customerDTO.getId());
        if (optCustomer.isEmpty())
            throw new NoSuchElementException("customer does not exist");

        Customer customer = optCustomer.get();

        customer.setName(customerDTO.getName());

        customerRepository.save(customer);
    }
}
