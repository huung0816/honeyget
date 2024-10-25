package com.example.honeyget.dto;


import jakarta.persistence.Table;
import lombok.Getter;

@Table(name = "product")
@Getter
public class MyListDTO {

    private String userId;
    private String title;
    private Double price;
    private String content;
}
