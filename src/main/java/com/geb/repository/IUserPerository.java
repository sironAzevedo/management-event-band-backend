package com.geb.repository;

import com.geb.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IUserPerository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    Boolean existsByEmail(String email);
    
    
    Optional<User> findByChaveChave(String chave);
    
//    @Query("select u from User u where u.associated.chave = :chave")
//    Optional<User> findByAssociatedChave(@Param("chave") String chave);
}
