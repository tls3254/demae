package com.example.demae.global.security.jwt.service;

import com.example.demae.domain.user.repository.UserRepository;
import com.example.demae.global.message.ErrorMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        com.example.demae.domain.user.entity.User user = userRepository.findByUserEmail(email).orElseThrow(() -> new UsernameNotFoundException(ErrorMessage.NOT_EXIST_EMAIL_ERROR_MESSAGE.getErrorMessage()));

        return User.builder()
                .username(user.getUserEmail())
                .password(user.getUserPassword())
                .authorities(user.getUserRole().getAuthority())
                .build();
    }
}
