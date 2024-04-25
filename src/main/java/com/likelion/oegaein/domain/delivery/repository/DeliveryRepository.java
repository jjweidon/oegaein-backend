package com.likelion.oegaein.domain.delivery.repository;

import com.likelion.oegaein.domain.delivery.Entity.Delivery;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeliveryRepository extends JpaRepository<Delivery, Long> {
}
