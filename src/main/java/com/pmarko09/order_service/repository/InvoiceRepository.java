package com.pmarko09.order_service.repository;

import com.pmarko09.order_service.model.entity.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Long> {

    @Query("SELECT i FROM Invoice i WHERE i.order.id = :orderId")
    Optional<Invoice> findByOrderId(@Param("orderId") Long orderId);
}