package com.diegonobretecnologia.ferramenteiro.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data // Gera getters, setters, equals, hashCode e toString
@NoArgsConstructor // Construtor vazio
@AllArgsConstructor // Construtor com todos os campos
public class UserDTO {
    private Long id;       // ID do usuário
    private String name;   // Nome do usuário
    private String email;  // Email do usuário
}
