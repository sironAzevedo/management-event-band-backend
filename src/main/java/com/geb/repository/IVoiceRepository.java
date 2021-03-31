package com.geb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.geb.model.Voice;

@Repository
public interface IVoiceRepository extends JpaRepository<Voice, Long> {

}
