package com.geb.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.geb.model.User;

@Repository
public interface IUserPerository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    Boolean existsByEmail(String email);
    
    Optional<User> findByChaveChave(String chave);
    
    @Query(value = "select * from {h-schema}tb_user tb "
    		+ "where tb.name like %:value% or tb.email like %:value% "
    		+ "and tb.type_user = 'PF' "
    		, nativeQuery = true)
    List<User> findUsersByNameOrEmail(@Param("value") String value);
    
//    @Query("select u from User u where u.associated.chave = :chave")
//    Optional<User> findByAssociatedChave(@Param("chave") String chave);
}
