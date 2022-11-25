package edu.uwb.meetme.resources;

import edu.uwb.meetme.models.Session;
import edu.uwb.meetme.models.SessionPayload;
import edu.uwb.meetme.models.User;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/meetme/api/v1")
public class SessionController {
    Logger logger = LoggerFactory.getLogger(SessionController.class);

    @Autowired
    private SessionService sessionService;

    @Autowired
    private UserService userService;

    @RequestMapping("/session/{id}")
    public Session getSession(@PathVariable Long id) {
        logger.info("Session information has been retrieve Id: " + id);
        return sessionService.getSession(id);
    }

    @RequestMapping(method= RequestMethod.POST, value="/session", produces = MediaType.APPLICATION_JSON_VALUE)
    public Session addSession(@RequestBody SessionPayload sessionPayload) {
        User owner = userService.getUser(sessionPayload.owner_id);
        Session session = new Session();
        session.setDuration(sessionPayload.duration);
        session.setOwner(owner);
        Session savedSession = sessionService.addSession(session);
        owner.setTrackingSessionId(savedSession.getId());
        return session;
    }

    @RequestMapping(method = RequestMethod.POST, value="/session/{id}/adduser/{userId}")
    public Session addUser(@PathVariable Long id, @PathVariable Long userId) {
        User user = userService.getUser(userId);
        user.setTrackingSessionId(id);
        userService.updateUser(user);
        Session session = sessionService.getSession(id);
        return session;
    }

    @RequestMapping(method = RequestMethod.POST, value="/session/{id}/start")
    public Session startSession(@PathVariable Long id) {
        LocalDateTime currentTime = LocalDateTime.now();
        Session session = sessionService.getReference(id);
        session.setStartTime(currentTime);
        return sessionService.updateSession(session);
    }

    @RequestMapping(method = RequestMethod.POST, value="/session/{id}/end")
    public Session endSession(@PathVariable Long id) {
        LocalDateTime currentTime = LocalDateTime.now();
        Session session = sessionService.getReference(id);
        session.setEndTime(currentTime);
        return sessionService.updateSession(session);
    }
}
