package com.geb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.geb.model.Role;
import com.geb.model.enums.PerfilEnum;

@Repository
public interface IRoleRepository extends JpaRepository<Role, Long> {

	Role findByPerfil(PerfilEnum name);
}
