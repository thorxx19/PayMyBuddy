package com.Pay.PayMyBuddy.repository;


import com.Pay.PayMyBuddy.model.Transfer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface TransferRepository extends JpaRepository<Transfer, Long> {

    Optional<Transfer> findFirstByIdDebtor_IdOrderByDateDesc(Long id);

    List<Transfer> findByIdDebtor_IdOrderByDateDesc(Long id);

}
