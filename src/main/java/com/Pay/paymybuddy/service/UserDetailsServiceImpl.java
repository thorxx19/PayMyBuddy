package com.Pay.paymybuddy.service;

import com.Pay.paymybuddy.model.JwtUserDetails;
import com.Pay.paymybuddy.model.Profil;
import com.Pay.paymybuddy.repository.ProfilRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private ProfilRepository profilRepository;

    /**
     * Method pour ajouter le mail au token
     *
     * @param mail le mail du profil
     * @return mail du profil
     * @throws UsernameNotFoundException exception
     */
    @Override
    public UserDetails loadUserByUsername(String mail) throws UsernameNotFoundException {
        Profil profil = profilRepository.findByMail(mail);
        return JwtUserDetails.create(profil);
    }

    /**
     * Method pour ajouter l'id au token
     *
     * @param id du profil
     * @return l'id du profil
     */
    public UserDetails loadUserById(UUID id) {
        Profil profil = profilRepository.findById(id).get();
        return JwtUserDetails.create(profil);
    }
}
