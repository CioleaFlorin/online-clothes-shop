package org.fasttrackit.onlineclothesshop;
import org.fasttrackit.onlineclothesshop.domain.Product;
import org.fasttrackit.onlineclothesshop.exception.ResourceNotFoundException;
import org.fasttrackit.onlineclothesshop.service.ProductService;
import org.fasttrackit.onlineclothesshop.transfer.SaveProductRequest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.TransactionSystemException;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;

@RunWith (SpringRunner.class)
@SpringBootTest
public class ProductServiceIntegrationTests {

    @Autowired
    private ProductService productService;


    @Test(expected = TransactionSystemException.class)
    public void testCreateProduct_whenInvalidRequest_thenThrowException() {
        SaveProductRequest request = new SaveProductRequest();
        //leaving the request properties with default null values
        //to validate the negative flow
        productService.createProduct(request);

    }

    @Test
    public void testGetProduct_whenExistingProduct_thenReturnProduct() {
        Product createdProduct = createProduct();
        Product product=productService.getProduct(createdProduct.getId());

        assertThat(product, notNullValue());
        assertThat(product.getId(), is(createdProduct.getId()));
        assertThat(product.getId(), greaterThan(0L));
        assertThat(product.getName(), is(createdProduct.getName()));
        assertThat(product.getDescription(), is(createdProduct.getDescription()));
        assertThat(product.getBrand(), is(createdProduct.getBrand()));
        assertThat(product.getPrice(), is(createdProduct.getPrice()));
        assertThat(product.getQuantity(), is(createdProduct.getQuantity()));


    }

    @Test
    public void testCreateProduct_whenValidRequest_thenProductIsSaved() {
        createProduct();
    }



    @Test(expected = ResourceNotFoundException.class)
    public void testGetProduct_whenNonExistingProduct_thenThrowResourcesNotFoundException(){
        productService.getProduct(9999999999L);


    }
    @Test
    public void testUpdateProduct_whenValidRequest_thenReturnUpdatedProduct() {
        Product createdProduct = createProduct();

        SaveProductRequest request=new SaveProductRequest();
        request.setName(createdProduct.getName()+" updated");
        request.setDescription(createdProduct.getDescription() + " updated");
        request.setBrand(createdProduct.getBrand()+ " updated");
        request.setPrice(createdProduct.getPrice() + 10);
        request.setQuantity(createdProduct.getQuantity() + 10);

        Product updatedProduct = productService.updateProduct(createdProduct.getId(), request);


    }

    private Product createProduct() {
        SaveProductRequest request = new SaveProductRequest();
        request.setName("T-Shirt");
        request.setBrand("Gucci");
        request.setPrice(55.0);
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
