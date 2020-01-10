package org.fasttrackit.onlineclothesshop.persistance;

import org.fasttrackit.onlineclothesshop.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

}
