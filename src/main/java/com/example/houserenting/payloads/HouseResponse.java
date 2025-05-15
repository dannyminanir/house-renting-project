package com.example.houserenting.payloads;

import java.time.LocalDateTime;

public class HouseResponse {
    public Integer id;
    public String title;
    public String description;
    public String location;
    public Double price;
    public Integer rooms;
    public Boolean available;
    public String imageUrl;
    public Integer landlordId;
    public String status;
    public LocalDateTime createdAt;
    public LocalDateTime updatedAt;
}
