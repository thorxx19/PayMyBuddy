package com.Pay.paymybuddy.repository;


import com.Pay.paymybuddy.model.Transfer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;


@Repository
public interface TransferRepository extends JpaRepository<Transfer, UUID> {

    List<Transfer> findFirstByIdDebtor_IdOrderByDateDesc(UUID id);

    List<Transfer> findByIdDebtor_IdOrderByDateDesc(UUID id);

}
