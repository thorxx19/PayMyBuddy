package com.Pay.PayMyBuddy.repository;

import com.Pay.PayMyBuddy.model.Profil;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface ProfilRepository extends JpaRepository<Profil, Long> {

    Profil findByName(String name);
    List<Profil> findByMail(String name);

    @Query("SELECT x FROM Profil x WHERE x.id = ?1")
    Profil findByProfilId(long id);
    boolean existsByMail(String mail);

    @Query("select p from Profil p where p.id = ?1")
    List<Profil> findByIdList(Long id);



}
