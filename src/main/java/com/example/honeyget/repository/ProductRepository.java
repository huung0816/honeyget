package com.example.honeyget.repository;


import com.example.honeyget.Entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Long> {

    //userId기준으로 데이터 조회
    List<ProductEntity> findByUserId(String userId);

    ProductEntity getProductById(Long id);
}
