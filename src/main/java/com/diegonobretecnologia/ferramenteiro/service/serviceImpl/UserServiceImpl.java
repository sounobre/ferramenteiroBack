package com.diegonobretecnologia.ferramenteiro.service.serviceImpl;

import com.diegonobretecnologia.ferramenteiro.dto.UserDTO;
import com.diegonobretecnologia.ferramenteiro.model.UserModel;
import com.diegonobretecnologia.ferramenteiro.repository.UserRepository;
import com.diegonobretecnologia.ferramenteiro.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<UserDTO> getAllUsers() {
        // Converte a lista de entidades User para uma lista de UserDTO
        return userRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<UserDTO> getUserById(Long id) {
        // Converte a entidade User em um Optional<UserDTO>
        return userRepository.findById(id)
                .map(this::convertToDTO);
    }

    @Override
    public UserDTO createUser(UserDTO userDTO) {
        UserModel user = convertToEntity(userDTO);
        UserModel savedUser = userRepository.save(user);
        return convertToDTO(savedUser);
    }

    @Override
    public UserDTO updateUser(Long id, UserDTO userDetails) {
        return userRepository.findById(id).map(existingUser -> {
            existingUser.setName(userDetails.getName());
            existingUser.setEmail(userDetails.getEmail());
            UserModel updatedUser = userRepository.save(existingUser);
            return convertToDTO(updatedUser);
        }).orElse(null);
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    // Método para converter User para UserDTO
    private UserDTO convertToDTO(UserModel user) {
        return new UserDTO(user.getId(), user.getName(), user.getEmail());
    }

    // Método para converter UserDTO para User
    private UserModel convertToEntity(UserDTO userDTO) {
        return new UserModel(userDTO.getId(), userDTO.getName(), userDTO.getEmail());
    }
}