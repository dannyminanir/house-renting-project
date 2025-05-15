package com.example.houserenting.repositories;

import com.example.houserenting.entities.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Integer> {
    List<Booking> findByTenantId(Integer tenantId);


}
