package org.fasttrackit.onlineclothesshop.steps;

import org.fasttrackit.onlineclothesshop.domain.Product;
import org.fasttrackit.onlineclothesshop.service.ProductService;
import org.fasttrackit.onlineclothesshop.transfer.SaveProductRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;

@Component
public class ProductSteps {
    @Autowired
    private ProductService productService;

    public Product createProduct() {
        SaveProductRequest request = new SaveProductRequest();
        request.setName("T-Shirt");
        request.setBrand("Gucci");
        request.setPrice(50.0);
        request.setQuantity(40);
        request.setDescription("Starter Men's Long Sleeve Tech T-Shirt");

        Product createdProduct = productService.createProduct(request);
        assertThat(createdProduct, notNullValue());
        assertThat(createdProduct.getId(), notNullValue());
        assertThat(createdProduct.getId(), greaterThan(0L));
        assertThat(createdProduct.getName(), is(request.getName()));
        assertThat(createdProduct.getDescription(), is(request.getDescription()));
        assertThat(createdProduct.getBrand(), is(request.getBrand()));
        assertThat(createdProduct.getPrice(), is(request.getPrice()));
        assertThat(createdProduct.getQuantity(), is(request.getQuantity()));
        return createdProduct;
    }
}
