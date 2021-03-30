package com.geb.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.geb.model.Band;
import com.geb.model.UserBand;

@Repository
public interface IUserBandRepository extends JpaRepository<UserBand, Long> {
	
	List<UserBand> findByBand(Band band);
	
	Optional<UserBand> findByUserCodigo(Long user);
	
	@Query("select ub from UserBand ub where ub.user.codigo = :user")
	List<UserBand> findAssociatedBandsByUser(@Param("user") Long user);
	
	@Modifying
	@Query("delete from UserBand ub where ub.band.codigo = :band and ub.user.codigo = :user")
	void disassociateMembers(@Param("band") Long band, @Param("user") Long user);

}
