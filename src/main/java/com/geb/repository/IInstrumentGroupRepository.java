package com.geb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.geb.model.InstrumentGroup;

@Repository
public interface IInstrumentGroupRepository extends JpaRepository<InstrumentGroup, Long> {

}
