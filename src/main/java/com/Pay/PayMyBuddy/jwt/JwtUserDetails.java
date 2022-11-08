package com.Pay.PayMyBuddy.jwt;


import com.Pay.PayMyBuddy.model.Profil;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;


@Getter
@Setter
public class JwtUserDetails implements UserDetails {


    private UUID id;
    private String username;
    private String password;
    private Collection<? extends GrantedAuthority> authorities;

    //todo ajouter javadoc
    private JwtUserDetails(UUID id, String username, String password, Collection<? extends GrantedAuthority> authorities){
       this.id = id;
       this.username = username;
       this.password = password;
       this.authorities = authorities;
    }

    public static JwtUserDetails create(Profil profil){
        List<GrantedAuthority> authorityList = new ArrayList<>();
        authorityList.add(new SimpleGrantedAuthority("profil"));
        return new JwtUserDetails(profil.getId(), profil.getName(), profil.getPassword(), authorityList);
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
