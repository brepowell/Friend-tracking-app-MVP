package edu.uwb.meetme.resources;

import edu.uwb.meetme.models.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Encapsulates the gym member information as UserDetails for authentication purposes
 */
public class MyUserDetails implements UserDetails {

//    private User member;

    private String username;
    private String password;

    private List<GrantedAuthority> authorities;

    public MyUserDetails(String username, String password) {
        this.username = username;
        this.password = password;
        String[] strRoles = {"USER"};
        this.authorities = Arrays.stream(strRoles).map(SimpleGrantedAuthority::new).collect(Collectors.toList());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
