package edu.uwb.meetme.resources;

import edu.uwb.meetme.models.Session;
import edu.uwb.meetme.models.User;
import edu.uwb.meetme.models.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * Service for interfacing with the Member table in the database
 */
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    /**
     * Retrieves all the Gym Members
     * @return  The List of Gym Members
     */
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        userRepository.findAll().forEach(users::add);
        return users;
    }

    /**
     * Retrieves the Gym Member with the given ID
     * @param id    The ID of the Gym member
     * @return  The Gym Member with the given ID
     */
    public User getUser(Long id) {
        return userRepository.findById(id).get();
    }

    /**
     * Retrieve the Gym Member with the given email address
     * @param email The email address of the Gym Member
     * @return  The Gym Member with the given ID
     */
    public User getUser(String email) {
        return userRepository.findByEmail(email).get();
    }

    /**
     * Adds a new member to the database
     * @param member    The member information
     * @return  The newly added member
     */
    public User addUser(User member) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(member.getPassword());
        member.setPassword(encodedPassword);
        return userRepository.save(member);
    }

    /**
     * Updates the Member information in the database
     * @param user    The information to be updated
     */
    public void updateUser(User user) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        userRepository.save(user);
    }

    /**
     * Deletes a Member from the database
     * @param id    The ID of the Gym Member to be deleted
     */
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public Set<Session> getOwnSessions(Long userId) {
        User user = userRepository.getById(userId);
        Set<Session> sessions = user.getOwnSessions();

        // Remove sessions that have ended
        Iterator<Session> itr = sessions.iterator();
        while (itr.hasNext()) {
            Session current = itr.next();
            if (current.getEndTime() != null) {
                sessions.remove(current);
            }
        }
        return sessions;
    }
}
