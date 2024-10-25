package com.example.honeyget.controller;

import com.example.honeyget.Entity.ProductEntity;
import com.example.honeyget.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.util.List;

@RestController
@CrossOrigin(origins = {"http://localhost:3001", "http://localhost:3000"})
@RequestMapping("/api/my")
public class MyListController {

    @Autowired
    private ProductRepository productRepository;

    private static final String UPLOAD_DIR = "C:/uploads"; // 외부 디렉토리 경로

    // 사용자 토큰을 통해 userId 가져와서 제품 조회
    @GetMapping("/products")
    public ResponseEntity<List<ProductEntity>> getProductsByUserId(@RequestHeader(value = "Authorization", required = false) String authHeader) {


        try {
            // Authorization 헤더에서 Bearer 토큰 추출
            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                String userId = authHeader.substring(7); // "Bearer " 다음의 토큰만 가져오기
                System.out.println("Fetching products for userId: " + userId);

                List<ProductEntity> products = productRepository.findByUserId(userId); // 특정 사용자 ID로 제품 조회

                System.out.println("Number of products found: " + products.size());
                if (products.isEmpty()) {
                    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
                }

                return ResponseEntity.ok(products);
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build(); // 토큰이 없거나 형식이 잘못된 경우
            }
        } catch (Exception e) {
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

}
