package org.javaacademy.flat_rent.repository;

import org.javaacademy.flat_rent.entity.Apartment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApartmentRepository extends JpaRepository<Apartment, Integer> {
}
