package com.example.honeyget.service;

import com.example.honeyget.Entity.ProductEntity; // 대문자 E로 수정
import com.example.honeyget.dto.ProductUpdateDTO;
import com.example.honeyget.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository; // ProductRepository 주입

    // 모든 상품 조회
    public List<ProductEntity> findAll() {
        return productRepository.findAll(); // 모든 상품 조회
    }

    // 상품 ID로 조회
    public Optional<ProductEntity> findById(Long id) {
        return productRepository.findById(id); // 상품 ID로 조회
    }

    // 상품 수정
    public ProductEntity updateProduct(Long id, ProductUpdateDTO updatedProduct) {
        // 기존 상품을 찾기
        ProductEntity existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));

        // 수정할 필드 업데이트
        existingProduct.setTitle(updatedProduct.getTitle());
        existingProduct.setPrice(updatedProduct.getPrice());
        existingProduct.setContent(updatedProduct.getContent());

        // 이미지 파일이 있을 경우 처리
        if (updatedProduct.getImage() != null && !updatedProduct.getImage().isEmpty()) {
            // 이미지 저장 경로 설정
            String imagePath = "uploads/" + updatedProduct.getImage().getOriginalFilename(); // 경로 설정

            // 이미지 파일 저장
            try {
                File imageFile = new File(imagePath);
                updatedProduct.getImage().transferTo(imageFile); // 파일 저장
                existingProduct.setImagePath(imagePath); // 새 이미지 경로 업데이트
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException("Could not save image file: " + e.getMessage());
            }
        }

        // 수정된 상품 저장
        return productRepository.save(existingProduct);
    }

    // 상품 삭제
    public void deleteProduct(Long id) {
        // 상품이 존재하는지 확인 후 삭제
        ProductEntity existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));
        productRepository.delete(existingProduct);
    }
}
