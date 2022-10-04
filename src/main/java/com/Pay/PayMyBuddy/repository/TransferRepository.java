package com.Pay.PayMyBuddy.repository;


import com.Pay.PayMyBuddy.model.Transfer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;




@Repository
public interface TransferRepository extends JpaRepository<Transfer, Long> {

    @Query("FROM Transfer WHERE idDebtor.id = ?1 order by date.date desc")
    Iterable<Transfer> findByIdAll(Long idDebtor);

}
