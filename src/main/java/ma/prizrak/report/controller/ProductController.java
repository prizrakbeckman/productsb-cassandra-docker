package ma.prizrak.report.controller;


import lombok.extern.slf4j.Slf4j;
import ma.prizrak.report.exception.ProductNotFoundException;
import ma.prizrak.report.model.Product;
import ma.prizrak.report.repository.ProductRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@Slf4j
public class ProductController {

    private final ProductRepository productRepository;

    public ProductController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @PostMapping("/products")
    public Product addProduct(@RequestBody Product product) {
        this.productRepository.save(product);
        log.info("product added ");
        product.setCategory(product.getCategory()+" product added");
        return product;
    }

    @GetMapping("/products")
    public List<Product> getProducts() {
        List<Product> products = this.productRepository.findAll();
        log.info("products got , in total : "+products.size());
        return products;
    }

    @PutMapping("/update/products/{id}")
    public Product update(@PathVariable(value="id") Integer productId, @RequestBody Product product) {
        Product p = this.productRepository.findById(productId)
                .orElseThrow(
                        () -> new ProductNotFoundException(" Product not found to be updated"));
        p.setName(product.getName());
        p.setCategory(product.getCategory());
        Product updatedProduct = this.productRepository.save(p);
        log.info("products modified ");
        return updatedProduct;
    }

    @DeleteMapping("/delete/product/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable(value="id") Integer productId, @RequestBody Product product){
        Product p = this.productRepository.findById(productId)
                .orElseThrow(
                        () -> new ProductNotFoundException(" Product not found to be updated"));
        this.productRepository.delete(product);
        log.info("products deleted ");
        return ResponseEntity.ok().build();
    }
}
