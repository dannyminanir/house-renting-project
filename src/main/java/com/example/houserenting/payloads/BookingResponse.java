package com.example.houserenting.payloads;

import java.time.LocalDateTime;

public class BookingResponse {
    public Integer id;
    public HouseResponse house;
    public Integer tenantId;
    public LocalDateTime bookingDate;
    public String status;
    public LocalDateTime createdAt;
    public LocalDateTime updatedAt;
}
