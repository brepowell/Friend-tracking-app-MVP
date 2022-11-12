package edu.uwb.meetme.resources;

import edu.uwb.meetme.models.User;
import edu.uwb.meetme.models.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

/**
 * Loads the Gym Member details as UserDetails for authentication purposes
 */
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    /**
     * Load the Member information as UserDetails for authentication purposes
     * @param email The email of the Gym Member
     * @return  The UserDetails for the given member
     * @throws UsernameNotFoundException
     */
    @Override
    public org.springframework.security.core.userdetails.UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        System.out.println("In Service...");
        Optional<User> user = userRepository.findByEmail(email);
        user.orElseThrow(() -> new UsernameNotFoundException("user not found: " + email));
        return user.map(UserDetails::new).get();
    }
}
