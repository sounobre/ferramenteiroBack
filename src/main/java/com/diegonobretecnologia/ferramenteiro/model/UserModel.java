package com.diegonobretecnologia.ferramenteiro.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@Data // Gera automaticamente Getters, Setters, Equals, HashCode e toString
@NoArgsConstructor // Construtor vazio
@AllArgsConstructor // Construtor com todos os atributos
public class UserModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto incremento para o ID
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false, unique = true, length = 150)
    private String email;
}
