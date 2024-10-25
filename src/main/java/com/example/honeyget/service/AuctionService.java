package com.example.honeyget.service;

import com.example.honeyget.Entity.AuctionEntity;
import com.example.honeyget.dto.AuctionDTO;
import com.example.honeyget.repository.AuctionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuctionService {

    @Autowired
    private AuctionRepository auctionRepository;

    public AuctionEntity saveAuction(AuctionDTO auctionDTO) {
        AuctionEntity auctionEntity = new AuctionEntity();
        auctionEntity.setPrice(auctionDTO.getPrice());
        return auctionRepository.save(auctionEntity);
    }
}
