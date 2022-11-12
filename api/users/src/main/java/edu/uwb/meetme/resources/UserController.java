package edu.uwb.meetme.resources;

import edu.uwb.meetme.models.User;
import edu.uwb.meetme.models.ResponseMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

/**
 * Rest Controller for the User Management API
 */
@RestController
@RequestMapping("/meetme/api/v1")
public class UserController {

    Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    /**
     * Get list of all users in the database
     * @return List of all users in the database
     */
    @RequestMapping("/users")
    public List<User> getAllUsers() {
        logger.info("All users information has been retrieved.");
        return userService.getAllUsers();
    }

    /**
     * Get all the information about the user with the give email address
     * @param email The email address of the user
     * @return  The information about the given user
     */
    @RequestMapping(value="/users", params="email", method = RequestMethod.GET)
    public User getUser(@RequestParam("email") String email) {
        logger.info("Information has been retrieved for user with email: " + email);
        return userService.getUser(email);
    }

    /**
     * Get all the information about the user with the given id
     * @param id The id of the user
     * @return The information about the given user
     */
    @RequestMapping("/users/{id}")
    public User getUser(@PathVariable Long id) {
        logger.info("Information has been retrieved for user with id: " + id);
        return userService.getUser(id);
    }

    /**
     * Add a new user to the database. The user information must be given in the request body in JSON format.
     * @param user    JSON object with all user information
     * @return  Success or failure message string
     */
    @RequestMapping(method= RequestMethod.POST, value="/users/signup", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseMessage addUser(@RequestBody User user) {
        try {
            userService.addUser(user);
            logger.info("Successfully created profile for user with email: " + user.getEmail());
            ResponseMessage responseMessage = new ResponseMessage("Your profile in the MeetMe app was successfully created.");
            return responseMessage;
        } catch (DataIntegrityViolationException ex) {
            logger.info("Sign Up Failed. Email already exists in the system:" + user.getEmail());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Sign Up Failed. Email already exists in the system: " + user.getEmail());
        } catch (RuntimeException ex) {
            logger.debug("Failed to create user profile.");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Failed to create user profile. Review your information and try again.");
        }
    }

    /**
     * Update information for the given user
     * @param user    JSON object with all user information
     */
    @RequestMapping(method=RequestMethod.PUT, value="/users/{id}")
    public ResponseMessage updateUser(@RequestBody User user) {
        try {
            userService.updateUser(user);
            logger.info("Record has been modified for user with email: " + user.getEmail());
            ResponseMessage responseMessage = new ResponseMessage("Your profile has been updated.");
            return responseMessage;
        } catch (Exception e) {
            logger.debug("Failed to update information for user with email: " + user.getEmail());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Failed to Update user information.");
        }
    }

    /**
     * Delete user with the given id
     * @param id    The id of the user to be deleted
     */
    @RequestMapping(method=RequestMethod.DELETE, value="/users/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        logger.info("Deleted user with id: " + id);
    }
}
