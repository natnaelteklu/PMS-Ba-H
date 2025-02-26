package com.pms.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pms.entity.Session;
import com.pms.repository.SessionDao;

@Service
public class ActiveSessionService {

	@Autowired
	private SessionDao sessionDao;

    public Session saveSessionData(Session session) {
    	return sessionDao.save(session);
    }
    
    public Session getSessionBySessionId(String sessionId) {
    	return sessionDao.findBysessionId(sessionId);
    }
}
