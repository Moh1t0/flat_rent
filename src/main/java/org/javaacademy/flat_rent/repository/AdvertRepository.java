package org.javaacademy.flat_rent.repository;

import org.javaacademy.flat_rent.entity.Advert;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdvertRepository extends JpaRepository<Advert, Integer> {
}
