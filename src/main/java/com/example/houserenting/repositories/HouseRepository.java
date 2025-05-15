package com.example.houserenting.repositories;

import com.example.houserenting.entities.House;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface HouseRepository extends JpaRepository<House, Integer> {

    @Query("SELECT h FROM House h WHERE (:minPrice IS NULL OR h.price >= :minPrice) AND (:maxPrice IS NULL OR h.price <= :maxPrice)")
    List<House> findByPriceRange(@Param("minPrice") Double minPrice, @Param("maxPrice") Double maxPrice);
}
