package com.company.productservice;

import com.company.productservice.dto.CreateProductRequest;
import com.company.productservice.entity.Product;
import com.company.productservice.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    // Health check
    @GetMapping("/health")
    public Map<String, String> health() {
        return Map.of(
                "service", "product-service",
                "status", "UP"
        );
    }

    // GET /api/products
    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        return ResponseEntity.ok(productService.getAllProducts());
    }

    // GET /api/products/{id}
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        return ResponseEntity.ok(productService.getProductById(id));
    }

    // POST /api/products
    @PostMapping
    public ResponseEntity<Product> createProduct(
            @Valid @RequestBody CreateProductRequest request) {

        Product product = new Product(
                request.getName(),
                request.getDescription(),
                request.getPrice(),
                request.getStockQuantity()
        );

        Product savedProduct = productService.createProduct(product);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(savedProduct);
    }

    // PUT /api/products/{id}
    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(
            @PathVariable Long id,
            @Valid @RequestBody CreateProductRequest request) {

        Product productDetails = new Product(
                request.getName(),
                request.getDescription(),
                request.getPrice(),
                request.getStockQuantity()
        );

        Product updatedProduct =
                productService.updateProduct(id, productDetails);

        return ResponseEntity.ok(updatedProduct);
    }

    // DELETE /api/products/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> deleteProduct(
            @PathVariable Long id) {

        productService.deleteProduct(id);

        return ResponseEntity.ok(
                Map.of(
                        "message", "Product deleted successfully",
                        "id", String.valueOf(id)
                )
        );
    }
}
