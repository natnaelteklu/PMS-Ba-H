package com.pms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pms.entity.Setting;

@Repository
public interface CustomizationDao extends JpaRepository<Setting, Long> {
	Setting findByid(Long id);

}
