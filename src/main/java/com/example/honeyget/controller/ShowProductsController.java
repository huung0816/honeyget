package com.example.honeyget.controller;

import com.example.honeyget.Entity.ProductEntity;
import com.example.honeyget.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.util.List;

@RestController
@CrossOrigin(origins = {"http://localhost:3001", "http://localhost:3000"})
@RequestMapping("/api")
public class ShowProductsController {

    @Autowired
    private ProductRepository productRepository;

    private static final String UPLOAD_DIR = "C:/uploads"; // 외부 디렉토리 경로

    @GetMapping("/products")
    public ResponseEntity<List<ProductEntity>> getProducts() {
        try {
            List<ProductEntity> products = productRepository.findAll(); // 모든 제품 조회

            // 조회된 제품이 없을 경우 204 No Content 응답
            if (products.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
            }

            // 200 OK 응답
            return ResponseEntity.ok(products);
        } catch (Exception e) {
            // 예외 발생 시 로그 기록 및 500 Internal Server Error 응답
            System.err.println("Error retrieving products: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/uploads/{filename:.+}")
    public ResponseEntity<Resource> serveFile(@PathVariable String filename) {
        File file = new File(UPLOAD_DIR, filename);
        if (!file.exists()) {
            return ResponseEntity.notFound().build();
        }

        Resource resource = new FileSystemResource(file);

        if (!file.canRead()) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null); // 권한이 없을 경우
        }

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

    @GetMapping("/ProductEx/{id}")
    public ResponseEntity<ProductEntity> getProductById(@PathVariable Long id) {
        ProductEntity product = productRepository.getProductById(id); // 서비스에서 상품 정보 가져오기

        if (product != null) {
            return ResponseEntity.ok(product);
        } else {
            return ResponseEntity.notFound().build(); // 상품이 없을 경우 404
        }
    }

}
