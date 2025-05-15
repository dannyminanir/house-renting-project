package com.example.houserenting.services;

import com.example.houserenting.entities.Booking;
import com.example.houserenting.entities.House;
import com.example.houserenting.payloads.*;
import com.example.houserenting.repositories.BookingRepository;
import com.example.houserenting.repositories.HouseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private HouseRepository houseRepository;

    public BookingResponse createBooking(BookingRequest request) {
        House house = houseRepository.findById(request.houseId)
                .orElseThrow(() -> new RuntimeException("House not found"));

        Booking booking = new Booking();
        booking.setHouse(house);
        booking.setTenantId(request.tenantId);
        booking.setStatus("pending");

        Booking saved = bookingRepository.save(booking);

        BookingResponse response = new BookingResponse();
        response.id = saved.getId();
        response.house = toHouseResponse(house);
        response.tenantId = saved.getTenantId();
        response.status = saved.getStatus();
        response.bookingDate = saved.getBookingDate();
        response.createdAt = saved.getCreatedAt();
        response.updatedAt = saved.getUpdatedAt();

        return response;
    }

    private HouseResponse toHouseResponse(House h) {
        HouseResponse res = new HouseResponse();
        res.id = h.getId();
        res.title = h.getTitle();
        res.description = h.getDescription();
        res.location = h.getLocation();
        res.price = h.getPrice();
        res.rooms = h.getRooms();
        res.available = h.getAvailable();
        res.imageUrl = h.getImageUrl();
        res.landlordId = h.getLandlordId();
        res.status = h.getStatus();
        res.createdAt = h.getCreatedAt();
        res.updatedAt = h.getUpdatedAt();
        return res;
    }
    public List<BookingResponse> getBookingsByTenant(Integer tenantId) {
        List<Booking> bookings = bookingRepository.findByTenantId(tenantId);

        return bookings.stream().map(b -> {
            BookingResponse res = new BookingResponse();
            res.id = b.getId();
            res.tenantId = b.getTenantId();
            res.status = b.getStatus();
            res.bookingDate = b.getBookingDate();
            res.createdAt = b.getCreatedAt();
            res.updatedAt = b.getUpdatedAt();
            res.house = toHouseResponse(b.getHouse());
            return res;
        }).toList();
    }


}
