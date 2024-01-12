package com.example.demae.security;


import com.example.demae.entity.User;
import com.example.demae.enums.UserRoleEnum;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;


@Slf4j(topic = "UserDetailsImpl")
@Getter
public class UserDetailsImpl implements UserDetails {
    private final User user;

    public UserDetailsImpl(User user){
        log.info(user.getEmail());
        this.user = user;
    }

    @Override
    public String getPassword(){
        return user.getPassword();
    }

    @Override
    public String getUsername(){
        return user.getEmail();
    }

    // 권한 설정 부분
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities(){
        UserRoleEnum role = user.getRole();
        String authority = role.getAuthority();
        log.info(authority);
        SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(authority);
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(simpleGrantedAuthority);

        return authorities;
    }

    @Override
    public boolean isAccountNonExpired(){
        return true;
    }

    @Override
    public boolean isAccountNonLocked(){
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired(){
        return true;
    }

    @Override
    public boolean isEnabled(){
        return true;
    }
}