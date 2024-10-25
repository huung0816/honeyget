package com.example.honeyget.controller;

import com.example.honeyget.Entity.AuctionEntity;
import com.example.honeyget.dto.AuctionDTO;
import com.example.honeyget.service.AuctionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auction")
@CrossOrigin(origins = "http://localhost:3000") // 프론트엔드의 주소와 일치하게 설정
public class AuctionController {

    @Autowired
    private AuctionService auctionService;

    // 새로운 경매 데이터 저장하기
    @PostMapping
    public ResponseEntity<AuctionEntity> createAuction(@RequestBody AuctionDTO auctionDTO) {
        // auctionDTO 인스턴스를 통해 getPrice() 호출
        System.out.println("Received price: " + auctionDTO);
        AuctionEntity savedAuction = auctionService.saveAuction(auctionDTO);
        return ResponseEntity.ok(savedAuction);
    }

}
