package com.geb.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.geb.model.Event;

@Repository
public interface IEventRepository extends JpaRepository<Event, Long> {
	
	List<Event> findByBandCodigo(Long band);

}
