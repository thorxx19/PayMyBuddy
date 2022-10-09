package com.Pay.PayMyBuddy.repository;

import com.Pay.PayMyBuddy.model.Profil;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ProfilRepository extends JpaRepository<Profil, Long> {

    @Query("SELECT x FROM Profil x WHERE x.name = ?1")
    List<Profil> findNotAll(String name);
    @Query("SELECT x FROM Profil x WHERE x.id = ?1")
    Profil findByProfilId(long id);
    boolean existsByMail(String mail);

}
