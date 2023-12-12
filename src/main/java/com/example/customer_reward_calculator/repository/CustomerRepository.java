package com.example.customer_reward_calculator.repository;

import com.example.customer_reward_calculator.entity.Customer;
import org.springframework.data.repository.CrudRepository;

public interface CustomerRepository extends CrudRepository<Customer, Long> {
}
