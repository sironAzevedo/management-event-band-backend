package com.geb.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.geb.model.Band;

@Repository
public interface IBandPerository extends JpaRepository<Band, Long> {
	
	@Query("select b from Band b where b.associated.chave = :chave")
	List<Band> findByUserPJ(@Param("chave") String chave);
	
	@Query(value = "select * from {h-schema}tb_band tb "
			+ "inner join {h-schema}tb_band_info tbi "
			+ "on tb.id = tbi.id_band "
			+ "inner join {h-schema}tb_user tu "
			+ "on tbi.id_user = tu.id "
			+ "where tu.email = ? "
			, nativeQuery = true)
	List<Band> findByUserPF(String email);
}
