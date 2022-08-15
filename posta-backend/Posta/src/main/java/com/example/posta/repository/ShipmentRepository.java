package com.example.posta.repository;

import com.example.posta.model.Shipment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShipmentRepository extends JpaRepository<Shipment, Long> {
    Shipment findByCode(String code);
}
