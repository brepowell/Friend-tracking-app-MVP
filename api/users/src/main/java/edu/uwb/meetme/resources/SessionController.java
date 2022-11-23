package edu.uwb.meetme.resources;

import edu.uwb.meetme.models.Session;
import edu.uwb.meetme.models.SessionPayload;
import edu.uwb.meetme.models.User;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

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
        return sessionService.addSession(session);
    }
}
