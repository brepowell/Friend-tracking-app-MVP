package edu.uwb.meetme.resources;

import edu.uwb.meetme.models.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Encapsulates the gym member information as UserDetails for authentication purposes
 */
public class UserDetails implements org.springframework.security.core.userdetails.UserDetails {

    private User member;
    private List<GrantedAuthority> authorities;

    public UserDetails(User user) {
        this.member = user;
        String[] strRoles = {"MEMBER"};
        this.authorities = Arrays.stream(strRoles).map(SimpleGrantedAuthority::new).collect(Collectors.toList());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return member.getPassword();
    }

    @Override
    public String getUsername() {
        return member.getEmail();
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

    public String getFullName() {
        return member.getFirstName() + " " + member.getLastName();
    }
}
