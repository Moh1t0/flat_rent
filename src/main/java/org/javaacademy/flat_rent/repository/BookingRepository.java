package org.javaacademy.flat_rent.repository;

import org.javaacademy.flat_rent.entity.Booking;
import org.javaacademy.flat_rent.entity.Client;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepository extends JpaRepository<Booking, Integer> {

    Page<Booking> findAllByClientEmail(String email, Pageable pageable);

    void deleteByClient(Client client);
}
