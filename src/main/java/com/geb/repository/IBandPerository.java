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
	
	@Query(value = "select * from {h-schema}tb_band tb "
		+ "left join {h-schema}tb_pj_associated_band tpab "
		+ "on tpab.id_band = tb.id "
		+ "left join {h-schema}tb_pj_chave tpc "
		+ "on tpc.chave = tpab.chave "
		+ "left join {h-schema}tb_band_info tbi "
		+ "on tbi.id_band = tb.id "
		+ "left join {h-schema}tb_user tu "
		+ "on (tu.id = tbi.id_user or tu.id = tpc.id_user ) "
		+ "where tu.email = :email "
		+ "and lower(tb.name) like lower(concat('%', concat(:name, '%'))) "
		,nativeQuery = true)
	List<Band> findBandsByName(@Param("email") String email, @Param("name") String name);
}
