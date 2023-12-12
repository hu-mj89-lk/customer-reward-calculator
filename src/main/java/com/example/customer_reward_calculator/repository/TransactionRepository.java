package com.example.customer_reward_calculator.repository;

import com.example.customer_reward_calculator.entity.Transaction;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TransactionRepository extends CrudRepository<Transaction, Long> {

    @Query(value = """
            SELECT t
            FROM   Transaction t
            WHERE  t.customer.id = :customerId
                   AND FUNCTION('YEAR', t.dateTime) = :year
                   AND FUNCTION('MONTH', t.dateTime) IN :months""")
    List<Transaction> findMonthlyTransactionByCustomer(Long customerId, Integer year, List<Integer> months);
}
