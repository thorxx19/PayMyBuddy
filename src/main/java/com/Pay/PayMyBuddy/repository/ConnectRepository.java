package com.Pay.PayMyBuddy.repository;


import com.Pay.PayMyBuddy.model.Connect;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConnectRepository extends JpaRepository<Connect, Long> {


    boolean existsByIdUn_IdAndIdDeux_Id(Long id, Long id1);

    List<Connect> findByIdUn_Id(Long id);
}
