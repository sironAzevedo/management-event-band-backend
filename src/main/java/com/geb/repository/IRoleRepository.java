package com.geb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.geb.model.Role;
import com.geb.model.enums.PerfilEnum;

@Repository
public interface IRoleRepository extends JpaRepository<Role, Long> {

	Role findByPerfil(PerfilEnum name);
	
	@Modifying
	@Query(value = "delete from {h-schema}tb_user_role ur where ur.id_user = ? and ur.id_role = ?", nativeQuery = true)
	void disassociatePerfilUser(Long user, Long perfil);
}
