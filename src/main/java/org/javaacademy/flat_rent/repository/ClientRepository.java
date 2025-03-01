package org.javaacademy.flat_rent.repository;

import org.javaacademy.flat_rent.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Integer> {
}
