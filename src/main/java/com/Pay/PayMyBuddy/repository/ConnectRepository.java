package com.Pay.PayMyBuddy.repository;


import com.Pay.PayMyBuddy.model.Connect;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ConnectRepository extends JpaRepository<Connect, UUID> {


    boolean existsByIdUn_IdAndIdDeux_Id(UUID id, UUID id1);

    List<Connect> findByIdUn_Id(UUID id);

}
