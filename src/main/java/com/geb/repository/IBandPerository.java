package com.geb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.geb.model.Band;

@Repository
public interface IBandPerository extends JpaRepository<Band, Long> {
     
}
