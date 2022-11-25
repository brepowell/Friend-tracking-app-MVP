package edu.uwb.meetme.models;

import com.fasterxml.jackson.annotation.*;
import org.hibernate.annotations.NaturalId;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
public class User {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NaturalId
    @Column(nullable = false, unique = true)
    private String email;

//    @JsonIgnore
    @Column(nullable = false)
    private String password;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Basic(optional = false)
    @Column(name = "join_date",
            insertable = false,
            updatable = false,
            columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime joinDate;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private Location location;

    @JsonInclude(JsonInclude.Include.ALWAYS)
    @JsonIgnore
    @OneToMany(mappedBy = "owner")
    private Set<Session> ownSessions = new HashSet<>();

    @JsonIgnore
    @ManyToOne(targetEntity = Session.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "session_id", insertable = false, updatable = false)
    private Session session;

    @Column(name = "session_id")
    private Long trackingSessionId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }


    public LocalDateTime getJoinDate() {
        return joinDate;
    }

    public void setJoinDate(LocalDateTime joinDate) {
        this.joinDate = joinDate;
    }

    public Set<Session> getOwnSessions() {
        return ownSessions;
    }

    public void setOwnSessions(Set<Session> ownSessions) {
        this.ownSessions = ownSessions;
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public Long getTrackingSessionId() {
        return trackingSessionId;
    }

    public void setTrackingSessionId(Long sessionId) {
        this.trackingSessionId = sessionId;
    }
}
