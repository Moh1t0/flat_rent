package org.javaacademy.flat_rent.repository;

import org.javaacademy.flat_rent.entity.Booking;
import org.javaacademy.flat_rent.entity.Client;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BookingRepository extends JpaRepository<Booking, Integer> {

    @Query("""
            select b from Booking b
            where b.client.email = :email
            """)
    Page<Booking> findClientEmail(@Param("email") String email, Pageable pageable);

    @Modifying
    @Query("delete from Booking b where b.client = :client")
    void deleteByClient(@Param("client") Client client);
}
