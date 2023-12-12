package com.example.customer_reward_calculator.service;

import com.example.customer_reward_calculator.dto.CustomerPointsDTO;
import com.example.customer_reward_calculator.entity.Customer;
import com.example.customer_reward_calculator.entity.Transaction;
import com.example.customer_reward_calculator.repository.CustomerRepository;
import com.example.customer_reward_calculator.repository.TransactionRepository;
import com.example.customer_reward_calculator.util.ApplicationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.math.BigDecimal.ZERO;

@Service
@RequiredArgsConstructor
public class PointsService {

    private final CustomerRepository customerRepository;
    private final TransactionRepository transactionRepository;

    public CustomerPointsDTO calculatePoints(Long customerId,
                                             Integer year,
                                             List<Integer> months) {
        Optional<Customer> optCustomer = customerRepository.findById(customerId);
        if (optCustomer.isEmpty())
            throw new NoSuchElementException("customer does not exist");

        Customer customer = optCustomer.get();

        List<CustomerPointsDTO.PointsDTO> monthlyPoints = calculateMonthlyPoints(customer, year, months);

        BigDecimal totalPoints = monthlyPoints.stream()
                .map(CustomerPointsDTO.PointsDTO::getPoints)
                .reduce(ZERO, BigDecimal::add);

        return CustomerPointsDTO.builder()
                .id(customer.getId())
                .name(customer.getName())
                .monthlyPoints(monthlyPoints)
                .totalPoints(totalPoints)
                .build();
    }

    private List<CustomerPointsDTO.PointsDTO> calculateMonthlyPoints(Customer customer,
                                                                     Integer year,
                                                                     List<Integer> months) {
        return transactionRepository.findMonthlyTransactionByCustomer(customer.getId(), year, months).stream()
                .collect(Collectors.groupingBy(Transaction::getMonth)).entrySet().stream()
                .map(entry -> {
                    Integer month = entry.getKey();
                    List<Transaction> transactions = entry.getValue();

                    BigDecimal points = transactions.stream()
                            .map(Transaction::getAmount)
                            .map(ApplicationUtil::calculatePoints)
                            .reduce(ZERO, BigDecimal::add);

                    return CustomerPointsDTO.PointsDTO.builder()
                            .month(month)
                            .points(points)
                            .build();
                })
                .toList();
    }

}
