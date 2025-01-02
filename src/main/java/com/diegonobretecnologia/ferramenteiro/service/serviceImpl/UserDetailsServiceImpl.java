package com.diegonobretecnologia.ferramenteiro.service.serviceImpl;



import com.diegonobretecnologia.ferramenteiro.model.UserLogin;
import com.diegonobretecnologia.ferramenteiro.repository.UserLoginRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private static final Logger logger = LoggerFactory.getLogger(UserDetailsServiceImpl.class);

    @Autowired
    private UserLoginRepository userLoginRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        logger.info("UserDetailsServiceImpl: Carregando usuário com email: {}", email);
        UserLogin userLogin = userLoginRepository.findByEmail(email)
                .orElseThrow(() -> {
                    logger.error("UserDetailsServiceImpl: Usuário com email {} não encontrado.", email);
                    return new UsernameNotFoundException("Usuário não encontrado");
                });
        logger.info("UserDetailsServiceImpl: Usuário encontrado: {}", userLogin.getEmail());
        return new User(userLogin.getEmail(), userLogin.getPassword(), Collections.emptyList());
    }
}