package com.example.honeyget.controller;

import com.example.honeyget.Entity.ProductEntity; // 대문자 E로 수정
import com.example.honeyget.dto.ProductUpdateDTO;
import com.example.honeyget.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/producted")
@CrossOrigin(origins = "http://localhost:3000")
public class ProductController {

    @Autowired
    private ProductService productService;

    // 상품 정보 가져오기
    @GetMapping("/{id}")
    public ResponseEntity<ProductEntity> getProductById(@PathVariable Long id) {
        return productService.findById(id)
                .map(ResponseEntity::ok) // 상품이 있을 때
                .orElse(ResponseEntity.notFound().build()); // 상품이 없을 때
    }

    // 모든 상품 정보 가져오기
    @GetMapping
    public ResponseEntity<List<ProductEntity>> getAllProducts() {
        List<ProductEntity> products = productService.findAll();
        return ResponseEntity.ok(products);
    }

    // !! 상품 수정하기 !!
    @PutMapping("/{id}")
    public ResponseEntity<ProductEntity> updateProduct(
            @PathVariable Long id,
            @ModelAttribute ProductUpdateDTO updatedProduct) {

        ProductEntity product = productService.updateProduct(id, updatedProduct);
        return ResponseEntity.ok(product);
    }

    // 상품 삭제하기
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build(); // 삭제 성공시 204 No Content 반환
    }

}
