package com.example.demae.global.security.jwt.filter;

import com.example.demae.domain.user.entity.User;
import com.example.demae.domain.user.repository.UserRepository;
import com.example.demae.global.security.jwt.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.core.authority.mapping.NullAuthoritiesMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationProcessingFilter extends OncePerRequestFilter {

    private static final String NO_CHECK_URL = "/login";
    private static final String FIND_ACCESS_TOKEN = "AccessToken 발견";
    private static final String NONE_ACCESS_TOKEN = "AccessToken 없음";
    private static final String TOKEN_HEADER = "Bearer##";
    private static final String ACCESS_TOKEN_NAME = "access_token";

    private final JwtService jwtService;
    private final UserRepository userRepository;
    private GrantedAuthoritiesMapper authoritiesMapper = new NullAuthoritiesMapper();

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (request.getRequestURI().equals(NO_CHECK_URL)) {
            filterChain.doFilter(request, response);
            return;
        }

        String refreshToken = jwtService.extractRefreshToken(request)
                .filter(jwtService::isTokenValid)
                .orElse(null);

        String accessToken = jwtService.extractAccessToken(request)
                .filter(jwtService::isTokenValid)
                .orElse(null);

        if (refreshToken == null && accessToken == null) {
            filterChain.doFilter(request, response);
            return;
        }

        if (accessToken != null) {
            checkAccessTokenAndAuthentication(request, response, filterChain);
            log.info(FIND_ACCESS_TOKEN);
        }
        if (accessToken == null) {
            checkRefreshTokenAndReIssueAccessToken(response, refreshToken, request,filterChain);
            log.info(NONE_ACCESS_TOKEN);
        }
    }

    public void checkRefreshTokenAndReIssueAccessToken(HttpServletResponse response, String refreshToken, HttpServletRequest request, FilterChain filterChain) throws ServletException, IOException{

        User user = userRepository.findByRefreshToken(TOKEN_HEADER + refreshToken).orElseThrow();
        String newAccessToken = jwtService.createAccessToken(user.getUserEmail());
        jwtService.sendAccessToken(response, newAccessToken);

        String realToken = jwtService.substringToken(newAccessToken);
        if (realToken != null && jwtService.isTokenValid(realToken)) {
            jwtService.extractEmail(realToken).flatMap(userRepository::findByUserEmail).ifPresent(this::saveAuthentication);
        }

        filterChain.doFilter(request, response);
    }

    public void checkAccessTokenAndAuthentication(HttpServletRequest request, HttpServletResponse response,
                                                  FilterChain filterChain) throws ServletException, IOException {
        Cookie[] cookies = request.getCookies();
        String accessToken = null;
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (ACCESS_TOKEN_NAME.equals(cookie.getName())) {
                    accessToken = cookie.getValue();
                    break;
                }
            }
        }

        accessToken = jwtService.substringToken(accessToken);
        if (accessToken != null && jwtService.isTokenValid(accessToken)) {
            jwtService.extractEmail(accessToken).flatMap(userRepository::findByUserEmail).ifPresent(this::saveAuthentication);
        }

        filterChain.doFilter(request, response);
    }

    public void saveAuthentication(User myMember) {
        String password = myMember.getUserPassword();

        UserDetails userDetailsUser = org.springframework.security.core.userdetails.User.builder()
                .username(myMember.getUserEmail())
                .password(password)
                .roles(myMember.getUserRole().name())
                .build();

        Authentication authentication =
                new UsernamePasswordAuthenticationToken(userDetailsUser, null,
                        authoritiesMapper.mapAuthorities(userDetailsUser.getAuthorities()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}
