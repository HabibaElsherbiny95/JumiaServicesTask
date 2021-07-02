package com.jumia.task.core.repository;

import com.jumia.task.core.model.Customer;
import org.springframework.data.repository.CrudRepository;

import java.util.Collection;

public interface CustomerRepository extends CrudRepository<Customer, Integer> { }
