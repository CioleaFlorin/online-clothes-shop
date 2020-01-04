package org.fasttrackit.onlineclothesshop.persistance;

import org.fasttrackit.onlineclothesshop.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product,Long> {
}
