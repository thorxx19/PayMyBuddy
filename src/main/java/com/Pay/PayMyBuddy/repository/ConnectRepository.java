package com.Pay.PayMyBuddy.repository;


import com.Pay.PayMyBuddy.model.Connect;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ConnectRepository extends JpaRepository<Connect, Long> {

    @Query("FROM Connect WHERE idUn.id = ?1")
    Iterable<Connect> findByIdAll(Long idUn);

}
