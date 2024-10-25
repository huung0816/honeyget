package com.example.honeyget.controller;

import com.example.honeyget.Entity.ProductEntity;
import com.example.honeyget.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.UUID;
import java.util.logging.Logger;

@RestController
@CrossOrigin(origins = {"http://localhost:3001", "http://localhost:3000"})
@RequestMapping("/api")
public class ProductWriteController {

    private static final Logger logger = Logger.getLogger(ProductWriteController.class.getName());

    @Autowired
    private ProductRepository productRepository;

    // 파일 업로드 경로
    @Value("${file.upload-dir}")
    private String uploadDir; //외부디렉토리 경로 프로퍼티즈에서 설정한거 주입시키기.

    @PostMapping("/ProductWrite")
    public ResponseEntity<?> productWrite(
            @RequestParam String userId, // 사용자 Id
            @RequestParam String title,
            @RequestParam Double price,
            @RequestParam MultipartFile image,
            @RequestParam String content) {

        String filePath = null;
        String imageUrl = null;

        try {
            // 이미지 파일 저장
            String originalFilename = null;
            if (image != null && !image.isEmpty()) {
                originalFilename = image.getOriginalFilename();
                String uniqueFilename = UUID.randomUUID() + "_" + originalFilename;
                filePath = Paths.get(uploadDir, uniqueFilename).toString();

                // 이미지 파일 저장
                image.transferTo(new File(filePath));
                // 이미지 URL 생성
                imageUrl = "/uploads/" + uniqueFilename; // URL 경로
            }

            // ProductEntity 생성 및 저장
            ProductEntity productEntity = new ProductEntity();
            productEntity.setUserId(userId);
            productEntity.setTitle(title);
            productEntity.setPrice(price);
            productEntity.setContent(content);
            productEntity.setImagePath(imageUrl); // 이미지 URL 저장
            productRepository.save(productEntity);

            logger.info("상품 등록 성공: 제목=" + title + ", 가격=" + price + ", 이미지=" + (image != null ? originalFilename : "없음"));

            // 응답에 이미지 URL 포함
            return ResponseEntity.status(HttpStatus.CREATED).body("상품 등록 완료, 이미지 URL: " + imageUrl);
        } catch (IOException e) {
            logger.severe("파일 저장 실패: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("파일 저장 실패");
        } catch (Exception e) {
            logger.severe("상품 등록 실패: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("상품 등록 실패");
        }
    }
}
