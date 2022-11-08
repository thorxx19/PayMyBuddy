package com.Pay.PayMyBuddy.service;

import com.Pay.PayMyBuddy.jwt.JwtUserDetails;
import com.Pay.PayMyBuddy.model.Profil;
import com.Pay.PayMyBuddy.repository.ProfilRepository;
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
    //todo ajouter javadoc
    @Override
    public UserDetails loadUserByUsername(String mail) throws UsernameNotFoundException {
        Profil profil = profilRepository.findByMail(mail);
        return JwtUserDetails.create(profil);
    }
    //todo ajouter javadoc
    public UserDetails loadUserById(UUID id){
        Profil profil = profilRepository.findById(id).get();
        return JwtUserDetails.create(profil);
    }
}
