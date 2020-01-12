package org.fasttrackit.onlineclothesshop.web;

import org.fasttrackit.onlineclothesshop.service.CartSerivce;
import org.fasttrackit.onlineclothesshop.transfer.AddProductToCartRequest;
import org.fasttrackit.onlineclothesshop.transfer.CartResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin
@RestController
@RequestMapping("/cart")
public class CartController {
    private final CartSerivce cartSerivce;

    @Autowired
    public CartController(CartSerivce cartSerivce) {
        this.cartSerivce = cartSerivce;
    }

    @PutMapping
    public ResponseEntity addProductToCart(@RequestBody @Valid AddProductToCartRequest request) {
        cartSerivce.addProductToCart(request);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CartResponse> getCart(@PathVariable Long id) {
        CartResponse cart = cartSerivce.getCart(id);
        return new ResponseEntity<>(cart, HttpStatus.OK);
    }
}
