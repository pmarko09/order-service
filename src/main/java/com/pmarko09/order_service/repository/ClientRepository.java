package com.pmarko09.order_service.repository;

import com.pmarko09.order_service.model.entity.Client;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {

    @Query("SELECT c FROM Client c")
    List<Client> findAllClients(Pageable pageable);

    Optional<Client> findByEmail(String email);
}