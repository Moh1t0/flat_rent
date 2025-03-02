package org.javaacademy.flat_rent.repository;


import org.javaacademy.flat_rent.entity.Advert;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;


public interface AdvertRepository extends JpaRepository<Advert, Integer> {

    Page<Advert> findByApartmentCity(String city, Pageable pageable);

    @Query("""
            select case when count(b) > 0 then true else false end
            from Booking b
            where b.advert = :advert
            and (b.startDate <= :endDate and b.endDate >= :startDate)
            """)
    boolean existsByAdvertAndDatesOverlap(@Param("advert") Advert advert,
                                          @Param("startDate") LocalDateTime startDate,
                                          @Param("endDate") LocalDateTime endDate);

}













