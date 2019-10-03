package ru.ustinov.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.validator.constraints.SafeHtml;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Collections;
import java.util.Set;

/**
 * //TODO add comments.
 *
 * @author Ivan Ustinov(ivanustinov1985@yandex.ru)
 * @version 1.0
 * @since 09.09.2019
 */
@Entity
@Table(name = "USERS", uniqueConstraints = {@UniqueConstraint(columnNames = {"EMAIL"}, name = "unique_email")})
public class User extends AbstractNamedEntity {

    @Column(name = "EMAIL", nullable = false, unique = true)
    @Email
    @NotBlank
    @Size(max = 100)
    @SafeHtml
    private String email;


    @Enumerated(EnumType.STRING)
    @Column(name = "role")
//    @ElementCollection(fetch = FetchType.EAGER)
    private Roles role;


    @Column(name = "PASSWORD", nullable = false)
    @NotBlank
    @Size(min = 5, max = 100)
    private String password;


    @Column(name = "enabled", nullable = false)
    private boolean enabled;


    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    @OrderBy("date DESC")
    @JsonIgnoreProperties("user")
    private Set<Vote> votes;

    public User() {
    }

    public User(User u) {
        this(u.getId(), u.getName(), u.getEmail(), u.getPassword(), u.getRole(), u.isEnabled());
    }

    public User(String name, String email, String password, Roles role) {
        super(name);
        this.email = email;
        this.password = password;
        this.role = role;
        this.enabled = true;
    }

    public User(Integer id, String name, String email, String password, Roles role) {
        this(id, name, email, password, role, true);
    }

    public User(Integer id, String name, String email, String password, Roles role, boolean enabled) {
        super(name);
        this.id = id;
        this.email = email;
        this.password = password;
        this.role = role;
        this.enabled = enabled;
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


    public Roles getRole() {
        return role;
    }

    public void setRole(Roles role) {
        this.role = role;

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }


    public Set<Vote> getVotes() {
        return votes;
    }

    public void setVotes(Set<Vote> votes) {
        if (votes.isEmpty())
            this.votes = Collections.emptySet();
        this.votes = votes;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
