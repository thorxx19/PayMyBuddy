package com.Pay.PayMyBuddy.repository;


import com.Pay.PayMyBuddy.model.Transfer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Repository
public interface TransferRepository extends JpaRepository<Transfer, UUID> {

    List<Transfer> findFirstByIdDebtor_IdOrderByDateDesc(UUID id);

    List<Transfer> findByIdDebtor_IdOrderByDateDesc(UUID id);

}
