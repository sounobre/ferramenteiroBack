package com.diegonobretecnologia.ferramenteiro.controller;

import com.diegonobretecnologia.ferramenteiro.dto.LoginResponseDto;
import com.diegonobretecnologia.ferramenteiro.dto.UserLoginDTO;
import com.diegonobretecnologia.ferramenteiro.model.UserLogin;
import com.diegonobretecnologia.ferramenteiro.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")

public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserLogin userLogin) {
        String message = authService.register(userLogin);
        return new ResponseEntity<>(message, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserLoginDTO userLoginDTO) {
        String token = authService.login(userLoginDTO);
        if (token != null) {
            LoginResponseDto loginResponseDto = new LoginResponseDto(token);
            return new ResponseEntity<>(loginResponseDto, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Credenciais inválidas", HttpStatus.UNAUTHORIZED);
        }
    }
}