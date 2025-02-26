package com.pms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pms.entity.Session;

import jakarta.transaction.Transactional;
@Repository
public interface SessionDao extends JpaRepository<Session, String>{

	Session findByuserName(String username);
 
	@Transactional
	void deleteByuserName(String username);

	Session findBysessionId(String sessionId);

}
