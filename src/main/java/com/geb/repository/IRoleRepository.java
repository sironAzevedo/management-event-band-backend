package com.geb.repository;

import com.geb.model.Role;
import com.geb.model.enums.PerfilEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface IRoleRepository extends JpaRepository<Role, Long> {

    Set<Role> findByPerfil(PerfilEnum name);
}
