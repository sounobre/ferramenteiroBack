package com.diegonobretecnologia.ferramenteiro.service;

import com.diegonobretecnologia.ferramenteiro.dto.UserLoginDTO;
import com.diegonobretecnologia.ferramenteiro.model.UserLogin;

public interface AuthService {
    String register(UserLogin userLogin);
    String login(UserLoginDTO userLoginDTO);
}