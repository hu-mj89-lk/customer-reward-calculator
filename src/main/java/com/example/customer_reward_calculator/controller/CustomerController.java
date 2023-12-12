package com.example.customer_reward_calculator.controller;

import com.example.customer_reward_calculator.api.customer.*;
import com.example.customer_reward_calculator.dto.CustomerDTO;
import com.example.customer_reward_calculator.dto.CustomerPointsDTO;
import com.example.customer_reward_calculator.exception.BadRequestException;
import com.example.customer_reward_calculator.exception.UnprocessableEntityException;
import com.example.customer_reward_calculator.service.CustomerService;
import com.example.customer_reward_calculator.service.PointsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.function.Predicate;

import static java.lang.Boolean.TRUE;

@RestController
@RequestMapping("customer")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;
    private final PointsService pointsService;

    private static final Predicate<List<Integer>> INVALID_MONTHS =
            months -> months.stream().anyMatch(month -> month < 1 || month > 12);

    @GetMapping("/{id}")
    public GetCustomerRS getCustomer(@PathVariable("id") Long id) {
        CustomerDTO customerDTO = CustomerDTO.builder()
                .id(id)
                .build();

        customerDTO = customerService.getCustomer(customerDTO);

        List<GetCustomerRS.TransactionRS> transactions = customerDTO.getTransactions().stream()
                .map(transactionDTO -> GetCustomerRS.TransactionRS.builder()
                        .id(transactionDTO.getId())
                        .amount(transactionDTO.getAmount())
                        .dateTime(transactionDTO.getDateTime())
                        .points(transactionDTO.getPoints())
                        .build())
                .toList();

        return GetCustomerRS.builder()
                .id(customerDTO.getId())
                .name(customerDTO.getName())
                .transactions(transactions)
                .build();
    }

    @PostMapping
    public CreateCustomerRS createCustomer(@RequestBody CreateCustomerRQ createCustomerRQ) {
        if (createCustomerRQ.getName() == null)
            throw new BadRequestException("name is mandatory");
        else if (createCustomerRQ.getName().isBlank())
            throw new UnprocessableEntityException("invalid name");

        CustomerDTO customerDTO = CustomerDTO.builder()
                .name(createCustomerRQ.getName())
                .build();

        customerDTO = customerService.createCustomer(customerDTO);

        return CreateCustomerRS.builder()
                .id(customerDTO.getId())
                .name(customerDTO.getName())
                .build();
    }

    @PutMapping("/{id}")
    public UpdateCustomerRS updateCustomer(@PathVariable("id") Long id,
                                           @RequestBody UpdateCustomerRQ updateCustomerRQ) {
        if (updateCustomerRQ.getName() == null)
            throw new BadRequestException("name is mandatory");
        else if (updateCustomerRQ.getName().isBlank())
            throw new UnprocessableEntityException("invalid name");

        CustomerDTO customerDTO = CustomerDTO.builder()
                .id(id)
                .name(updateCustomerRQ.getName())
                .build();

        customerService.updateCustomer(customerDTO);

        return UpdateCustomerRS.builder()
                .id(id)
                .status(TRUE)
                .build();
    }

    @PostMapping("/points")
    public CustomerPointsRS customerPoints(@RequestBody CustomerPointsRQ customerPointsRQ) {
        if (customerPointsRQ.getCustomerId() == null)
            throw new BadRequestException("customerId is mandatory");
        else if (customerPointsRQ.getCustomerId() < 1)
            throw new UnprocessableEntityException("invalid customerId");
        else if (customerPointsRQ.getYear() == null)
            throw new BadRequestException("year is mandatory");
        else if (customerPointsRQ.getYear() <= 0)
            throw new UnprocessableEntityException("invalid year");
        else if (customerPointsRQ.getMonths() == null || customerPointsRQ.getMonths().isEmpty())
            throw new BadRequestException("months is mandatory");
        else if (INVALID_MONTHS.test(customerPointsRQ.getMonths()))
            throw new UnprocessableEntityException("invalid month");

        CustomerPointsDTO customerPointsDTO =
                pointsService.calculatePoints(
                        customerPointsRQ.getCustomerId(),
                        customerPointsRQ.getYear(),
                        customerPointsRQ.getMonths()
                );

        List<CustomerPointsRS.PointRS> monthlyPoints = customerPointsDTO.getMonthlyPoints().stream()
                .map(pointsDTO -> CustomerPointsRS.PointRS.builder()
                        .month(pointsDTO.getMonth())
                        .points(pointsDTO.getPoints())
                        .build())
                .toList();

        return CustomerPointsRS.builder()
                .id(customerPointsDTO.getId())
                .name(customerPointsDTO.getName())
                .monthlyPoints(monthlyPoints)
                .totalPoints(customerPointsDTO.getTotalPoints())
                .build();
    }

}
