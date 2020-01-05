package org.fasttrackit.onlineclothesshop.service;

import org.fasttrackit.onlineclothesshop.domain.Product;
import org.fasttrackit.onlineclothesshop.exception.ResourceNotFoundException;
import org.fasttrackit.onlineclothesshop.persistance.ProductRepository;
import org.fasttrackit.onlineclothesshop.transfer.GetProductsRequest;
import org.fasttrackit.onlineclothesshop.transfer.SaveProductRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.nio.file.LinkOption;

@Service
public class ProductService {

    private static final Logger Logger=
            LoggerFactory.getLogger(ProductService.class);

    //IoC-Inversion of Control
    private final ProductRepository productRepository;

    //Dependency injection
    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product createProduct(SaveProductRequest request){
        Logger.info("Creating product {}",request);
        Product product=new Product();
        product.setDescription(request.getDescription());
        product.setBrand(request.getBrand());
        product.setImageUrl(request.getImageUrl());
        product.setPrice(request.getPrice());
        product.setName(request.getName());
        product.setQuantity(request.getQuantity());

        return productRepository.save(product);

    }

    public Product getProduct(long id){
        Logger.info("Retriving product {}", id);
        //using Optional
        return productRepository.findById(id)
                //lambda expression
                .orElseThrow(()->new ResourceNotFoundException(
                        "Product "+id+" does not exist."));
    }

    public Product updateProduct(long id, SaveProductRequest request){
        Logger.info("Updating product {}: {}", id, request);
        Product product=getProduct(id);

        BeanUtils.copyProperties(request,product);
        return productRepository.save(product);

    }

    public void deleteProduct(long id){
        Logger.info("Deleting product {}", id);
        productRepository.deleteById(id);
        Logger.info("Deleted product {}", id);
    }

    public Page<Product> getProducts(GetProductsRequest request, Pageable pageable) {
        Logger.info("Retriving products: {}", request );
        if (request != null && request.getPartialName() != null && request.getMinQuantity() != null) {
            return productRepository.findByNameContainingAndQuantityGreaterThanEqual(
                    request.getPartialName(), request.getMinQuantity(), pageable);
        } else if (request != null && request.getPartialName() != null){
            return productRepository.findByNameContaining(request.getPartialName(), pageable);
        }else {
            return productRepository.findAll(pageable);
        }
    }
}



