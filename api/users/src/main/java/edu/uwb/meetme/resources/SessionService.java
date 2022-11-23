package edu.uwb.meetme.resources;

import edu.uwb.meetme.models.Session;
import edu.uwb.meetme.models.SessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SessionService {

    @Autowired
    private SessionRepository sessionRepository;

    public Session getSession(Long id) {
        return sessionRepository.findById(id).get();
    }

    public Session addSession(Session session) {
        return sessionRepository.save(session);
    }
}
