package com.example.honeyget.dto;


import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class ProductUpdateDTO {
    private String title;
    private double price;
    private String content;
    private MultipartFile image;

    private String imagePath;

    // Getters and Setters
}

