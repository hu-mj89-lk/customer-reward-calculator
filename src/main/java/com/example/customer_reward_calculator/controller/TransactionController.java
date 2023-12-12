package com.example.customer_reward_calculator.controller;

import com.example.customer_reward_calculator.api.transaction.*;
import com.example.customer_reward_calculator.dto.TransactionDTO;
import com.example.customer_reward_calculator.exception.BadRequestException;
import com.example.customer_reward_calculator.exception.UnprocessableEntityException;
import com.example.customer_reward_calculator.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import static java.lang.Boolean.TRUE;
import static java.math.BigDecimal.ZERO;

@RestController
@RequestMapping("transaction")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    @GetMapping("/{id}")
    public GetTransactionRS getTransaction(@PathVariable("id") Long id) {
        TransactionDTO transactionDTO = TransactionDTO.builder()
                .id(id)
                .build();

        transactionDTO = transactionService.getTransaction(transactionDTO);

        return GetTransactionRS.builder()
                .id(transactionDTO.getId())
                .customerId(transactionDTO.getCustomerId())
                .amount(transactionDTO.getAmount())
                .dateTime(transactionDTO.getDateTime())
                .points(transactionDTO.getPoints())
                .build();
    }

    @PostMapping
    public CreateTransactionRS createTransaction(@RequestBody CreateTransactionRQ createTransactionRQ) {
        if (createTransactionRQ.getCustomerId() == null)
            throw new BadRequestException("customerId is mandatory");
        else if (createTransactionRQ.getCustomerId() < 1)
            throw new UnprocessableEntityException("invalid customerId");
        else if (createTransactionRQ.getAmount() == null)
            throw new BadRequestException("amount is mandatory");
        else if (createTransactionRQ.getAmount().compareTo(ZERO) < 0)
            throw new UnprocessableEntityException("invalid amount");

        TransactionDTO transactionDTO = TransactionDTO.builder()
                .customerId(createTransactionRQ.getCustomerId())
                .amount(createTransactionRQ.getAmount())
                .build();

        transactionDTO = transactionService.createTransaction(transactionDTO);

        return CreateTransactionRS.builder()
                .id(transactionDTO.getId())
                .customerId(transactionDTO.getCustomerId())
                .amount(transactionDTO.getAmount())
                .dateTime(transactionDTO.getDateTime())
                .points(transactionDTO.getPoints())
                .build();
    }

    @PutMapping("/{id}")
    public UpdateTransactionRS updateTransaction(@PathVariable("id") Long id,
                                                 @RequestBody UpdateTransactionRQ updateTransactionRQ) {
        if (updateTransactionRQ.getCustomerId() == null && updateTransactionRQ.getAmount() == null)
            throw new BadRequestException("either customerId or amount mandatory be provided");
        else if (updateTransactionRQ.getCustomerId() != null && updateTransactionRQ.getCustomerId() < 1)
            throw new UnprocessableEntityException("invalid customerId");
        else if (updateTransactionRQ.getAmount() != null && updateTransactionRQ.getAmount().compareTo(ZERO) < 0)
            throw new UnprocessableEntityException("invalid amount");

        TransactionDTO transactionDTO = TransactionDTO.builder()
                .id(id)
                .customerId(updateTransactionRQ.getCustomerId())
                .amount(updateTransactionRQ.getAmount())
                .build();

        transactionService.updateTransaction(transactionDTO);

        return UpdateTransactionRS.builder()
                .id(transactionDTO.getId())
                .status(TRUE)
                .build();
    }
}
