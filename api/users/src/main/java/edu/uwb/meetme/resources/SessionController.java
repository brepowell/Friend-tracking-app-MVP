package edu.uwb.meetme.resources;

import edu.uwb.meetme.models.Session;
import edu.uwb.meetme.models.SessionPayload;
import edu.uwb.meetme.models.User;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

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
    public Session getSession(@PathVariable Long id, @AuthenticationPrincipal UserDetails principal) {
        // Check that the logged user belongs to this tracking session
        User loggedUser = userService.getUser(principal.getUsername());
        if (!loggedUser.getTrackingSessionId().equals(id)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,
                      "User not allow to access to retrieve information for session with id: " + id);
        }

        logger.info("Session information has been retrieve Id: " + id);
        return sessionService.getSession(id);
    }

    @RequestMapping(method= RequestMethod.POST, value="/session", produces = MediaType.APPLICATION_JSON_VALUE)
    public Session addSession(@RequestBody SessionPayload sessionPayload,
                              @AuthenticationPrincipal UserDetails principal) {
        User owner = userService.getUser(sessionPayload.owner_id);
        // Check that the owner is the currently logged user
        if (!owner.getEmail().equals(principal.getUsername())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Wrong session Owner ID");
        }

        Session session = new Session();
        session.setDuration(sessionPayload.duration);
        session.setOwner(owner);
        Session savedSession = sessionService.addSession(session);
        owner.setTrackingSessionId(savedSession.getId());
        userService.updateUser(owner);
        return sessionService.getSession(savedSession.getId());
    }

    @RequestMapping(method = RequestMethod.POST, value="/session/{id}/adduser/{userId}")
    public Session addUser(@PathVariable Long id, @PathVariable Long userId,
                           @AuthenticationPrincipal UserDetails principal) {
        User user = userService.getUser(userId);
        // Check that the user to be added is the authenticated one
        if (!principal.getUsername().equals(user.getEmail())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You are not allowed to add the user to a session");
        }

        // Check that the session has not ended yet
        Session currentSession = sessionService.getSession(id);
        if (currentSession.getEndTime() == null) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "This session has already expired");
        }

        user.setTrackingSessionId(id);
        userService.updateUser(user);
        Session session = sessionService.getSession(id);
        return session;
    }

    @RequestMapping(method = RequestMethod.POST, value="/session/{id}/start")
    public Session startSession(@PathVariable Long id) {
        LocalDateTime currentTime = LocalDateTime.now();
        Session session = sessionService.getReference(id);

        // Check that session hasn't started yet
        if (session.getStartTime() != null) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Failed: Session has already been started.");
        }

        session.setStartTime(currentTime);
        return sessionService.updateSession(session);
    }

    @RequestMapping(method = RequestMethod.POST, value="/session/{id}/end")
    public Session endSession(@PathVariable Long id) {
        LocalDateTime currentTime = LocalDateTime.now();
        Session session = sessionService.getReference(id);

        // Check that session has been started
        if (session.getStartTime() == null) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Failed: Session has not started yet.");
        }

        // Check that end time is greater than start time, this should never happen
//        if (session.getStartTime().compareTo(currentTime) > 0) {
//            throw new ResponseStatusException(HttpStatus.CONFLICT, "Failed: Start time is after end time");
//        }

        session.setEndTime(currentTime);
        return sessionService.updateSession(session);
    }
}
