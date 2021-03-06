package com.geb.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.geb.model.BandInfo;
import com.geb.model.BandInfoPk;
import com.geb.model.enums.LeaderEnum;

@Repository
public interface IBandInfoRepository extends JpaRepository<BandInfo, BandInfoPk> {
	
	@Modifying
	@Query("delete from BandInfo ub where ub.codigo.band.codigo = :band and ub.codigo.user.codigo = :user")
	void disassociateMembers(@Param("band") Long band, @Param("user") Long user);
	
	@Query("select ub from BandInfo ub where ub.codigo.user.codigo = :user and ub.leader = :leader ")
	List<BandInfo> findByBandsLeaderByUser(@Param("user") Long user, @Param("leader") LeaderEnum leader);

}
