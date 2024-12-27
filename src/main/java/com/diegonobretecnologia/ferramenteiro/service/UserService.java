package com.diegonobretecnologia.ferramenteiro.service;

import com.diegonobretecnologia.ferramenteiro.dto.UserDTO;
import com.diegonobretecnologia.ferramenteiro.model.UserModel;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<UserDTO> getAllUsers();
    Optional<UserDTO> getUserById(Long id);
    UserDTO createUser(UserDTO userDTO);
    UserDTO updateUser(Long id, UserDTO userDetails);
    void deleteUser(Long id);
}
