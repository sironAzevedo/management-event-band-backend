package com.geb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.geb.model.UserBand;

@Repository
public interface IUserBandRepository extends JpaRepository<UserBand, Long> {

}
