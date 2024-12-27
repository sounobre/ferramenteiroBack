package com.diegonobretecnologia.ferramenteiro.repository;


import com.diegonobretecnologia.ferramenteiro.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserModel, Long> {

}