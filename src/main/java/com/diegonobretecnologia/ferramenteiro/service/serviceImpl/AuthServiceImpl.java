package com.diegonobretecnologia.ferramenteiro.service.serviceImpl;

import com.diegonobretecnologia.ferramenteiro.config.JwtUtil;
import com.diegonobretecnologia.ferramenteiro.dto.UserLoginDTO;
import com.diegonobretecnologia.ferramenteiro.model.UserLogin;
import com.diegonobretecnologia.ferramenteiro.repository.UserLoginRepository;
import com.diegonobretecnologia.ferramenteiro.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private UserLoginRepository userLoginRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public String login(UserLoginDTO userLoginDTO) {
        UserLogin userLogin = userLoginRepository.findByEmail(userLoginDTO.email())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Credenciais inválidas"));

        if (passwordEncoder.matches(userLoginDTO.password(), userLogin.getPassword())) {
            return jwtUtil.generateToken(userLogin.getEmail(), "USER");
        } else {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Credenciais inválidas");
        }
    }

    @Override
    public String register(UserLogin userLogin) {
        userLogin.setPassword(passwordEncoder.encode(userLogin.getPassword()));
        userLoginRepository.save(userLogin);
        return "Usuário criado com sucesso";
    }
}