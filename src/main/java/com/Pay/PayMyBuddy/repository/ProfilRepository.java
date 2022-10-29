package com.Pay.PayMyBuddy.repository;

import com.Pay.PayMyBuddy.model.Profil;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface ProfilRepository extends JpaRepository<Profil, Long> {

    Profil findByName(String name);
    Profil findByMail(String name);

    @Query("SELECT x FROM Profil x WHERE x.id = ?1")
    Profil findByProfilId(long id);
    boolean existsByMail(String mail);

    @Query("select p from Profil p where p.id = ?1")
    List<Profil> findByIdList(Long id);

    @Query("select p from Profil p where p.id = ?1")
    Profil findUniqueProfil(Long id);

    @Query(
            value = "SELECT * FROM client p\n" +
                    "LEFT JOIN connection c ON p.client_id = c.id_client_deux\n" +
                    "WHERE p.client_id <> ?1 AND (c.id_client_un IS NULL or c.id_client_un <> ?1)\n" +
                    "ORDER BY p.mail ASC",
            nativeQuery = true)
    List<Profil> findTest(Long id);

    List<Profil> findByIdNot(Long id);
}
