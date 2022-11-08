package com.Pay.PayMyBuddy.repository;

import com.Pay.PayMyBuddy.model.Profil;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Repository
public interface ProfilRepository extends JpaRepository<Profil, UUID> {

    Profil findByMail(String name);

    @Query("SELECT x FROM Profil x WHERE x.id = ?1")
    Profil findByProfilUuid(UUID id);

    @Query("select p from Profil p where p.id = ?1")
    List<Profil> findByIdList(UUID id);

    @Query("select p from Profil p where p.id = ?1")
    Profil findUniqueProfil(UUID id);

    List<Profil> findByIdNot(UUID id);

    @Query("select (count(p) > 0) from Profil p where p.id = :id")
    boolean existsByIdEquals(@Param("id") UUID id);

    @Override
    void deleteById(UUID id);
}
