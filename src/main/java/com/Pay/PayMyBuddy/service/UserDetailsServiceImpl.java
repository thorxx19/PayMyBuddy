package com.Pay.PayMyBuddy.service;

import com.Pay.PayMyBuddy.jwt.JwtUserDetails;
import com.Pay.PayMyBuddy.model.Profil;
import com.Pay.PayMyBuddy.repository.ProfilRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private ProfilRepository profilRepository;

    public UserDetailsServiceImpl(ProfilRepository profilRepository){
        this.profilRepository = profilRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Profil profil = profilRepository.findByName(username);
        return JwtUserDetails.create(profil);
    }

    public UserDetails loadUserById(Long id){
        Profil profil = profilRepository.findById(id).get();
        return JwtUserDetails.create(profil);
    }
}