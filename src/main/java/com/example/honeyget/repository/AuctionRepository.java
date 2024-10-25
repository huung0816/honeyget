package com.example.honeyget.repository;

import com.example.honeyget.Entity.AuctionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuctionRepository extends JpaRepository<AuctionEntity, Integer> {

}
