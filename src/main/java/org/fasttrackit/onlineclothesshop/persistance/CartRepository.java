package org.fasttrackit.onlineclothesshop.persistance;

import org.fasttrackit.onlineclothesshop.domain.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long> {

}
