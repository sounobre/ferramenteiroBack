package com.diegonobretecnologia.ferramenteiro.config;


import com.diegonobretecnologia.ferramenteiro.service.serviceImpl.UserDetailsServiceImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

@Component
public class JwtFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(JwtFilter.class);

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        logger.info("JwtFilter: Iniciando filtro para a requisição: {}", request.getRequestURI());
        final String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (request.getRequestURI().equals("/auth/login") || request.getRequestURI().equals("/auth/register")) {
            logger.info("JwtFilter: Requisição para /auth/login ou /auth/register, permitindo acesso.");
            filterChain.doFilter(request, response);
            return;
        }

        if (StringUtils.isEmpty(authorizationHeader) || !authorizationHeader.startsWith("Bearer ")) {
            logger.info("JwtFilter: Cabeçalho Authorization inválido ou ausente, permitindo acesso.");
            filterChain.doFilter(request, response);
            return;
        }

        final String token = authorizationHeader.substring(7);
        logger.info("JwtFilter: Token JWT extraído: {}", token);

        final String email = jwtUtil.getEmailFromToken(token);
        logger.info("JwtFilter: Email extraído do token: {}", email);

        final String role = jwtUtil.getRoleFromToken(token);
        logger.info("JwtFilter: Papel extraído do token: {}", role);

        if (!StringUtils.isEmpty(email) && SecurityContextHolder.getContext().getAuthentication() == null) {
            logger.info("JwtFilter: Email não está vazio e não há autenticação no contexto.");
            UserDetails userDetails = userDetailsService.loadUserByUsername(email);
            logger.info("JwtFilter: UserDetails obtido para o email: {}", email);

            if (jwtUtil.isTokenValid(token)) {
                logger.info("JwtFilter: Token JWT é válido.");
                List<SimpleGrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority(role));
                logger.info("JwtFilter: Autoridades do usuário: {}", authorities);
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, authorities
                );
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                logger.info("JwtFilter: Autenticação definida no contexto.");
            } else {
                logger.warn("JwtFilter: Token JWT é inválido.");
            }
        } else {
            logger.warn("JwtFilter: Email está vazio ou já existe autenticação no contexto.");
        }
        logger.info("JwtFilter: Finalizando filtro para a requisição: {}", request.getRequestURI());
        filterChain.doFilter(request, response);
    }
}