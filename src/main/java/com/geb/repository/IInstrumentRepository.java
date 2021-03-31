package com.geb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.geb.model.Instrument;

@Repository
public interface IInstrumentRepository extends JpaRepository<Instrument, Long> {

}
