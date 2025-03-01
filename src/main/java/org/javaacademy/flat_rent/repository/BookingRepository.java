package org.javaacademy.flat_rent.repository;

import org.javaacademy.flat_rent.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepository extends JpaRepository<Booking, Integer> {
}
