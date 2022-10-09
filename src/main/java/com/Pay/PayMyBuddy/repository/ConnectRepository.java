package com.Pay.PayMyBuddy.repository;


import com.Pay.PayMyBuddy.model.Connect;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConnectRepository extends JpaRepository<Connect, Long> {

    @Query("FROM Connect WHERE idUn.id = ?1")
    Iterable<Connect> findByIdAll(Long idUn);

    @Query("FROM Connect WHERE idUn.id = ?1 AND idDeux.id = ?2")
    List<Connect> existsById(long idUn, long idDeux);
}
