package bg.codeacademy.spring.gossiptalks.model;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
@Entity
public class User implements UserDetails {

  @Id
  @GeneratedValue
  private long id;
  private String name;
  @NotNull
  @Pattern(regexp = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")
  @Column(unique = true)
  private String email;
  @NotNull
  @Pattern(regexp = "^[a-z0-8\\.\\-]+$")
  @Column(unique = true)
  private String username;
  @NotNull
  @Size(max = 1024)
  private String password;
  @NotNull
  private OffsetDateTime registrationTime;
  private OffsetDateTime lastLoginTime;
  private boolean following;
  private boolean admin;
  @ManyToMany
  private ArrayList<User> users;
  @ElementCollection
  private List<String> roles = new ArrayList(Arrays.asList("admin", "users"));


  public long getId() {
    return id;
  }

  public User setId(int id) {
    this.id = id;
    return this;
  }

  public String getName() {
    return name;
  }

  public User setName(String name) {
    this.name = name;
    return this;
  }

  public String getEmail() {
    return email;
  }

  public User setEmail(String email) {
    this.email = email;
    return this;
  }

  public String getUsername() {
    return username;
  }

  public User setUsername(String username) {
    this.username = username;
    return this;
  }

  public String getPassword() {
    return password;
  }

  public User setPassword(String password) {
    this.password = password;
    return this;
  }

  public OffsetDateTime getLastLoginTime() {
    return lastLoginTime;
  }

  public User setLastLoginTime(OffsetDateTime lastLoginTime) {
    this.lastLoginTime = lastLoginTime;
    return this;
  }

  public OffsetDateTime getRegistrationTime() {
    return registrationTime;
  }

  public User setRegistrationTime(OffsetDateTime registrationTime) {
    this.registrationTime = registrationTime;
    return this;
  }

  public boolean isFollowing() {
    return following;
  }

  public User setFollowing(boolean following) {
    this.following = following;
    return this;
  }

  public ArrayList<User> getUsers() {
    return users;
  }

  public User setUsers(ArrayList<User> users) {
    this.users = users;
    return this;
  }

  public boolean isAdmin() {
    return admin;
  }

  public User setAdmin(boolean admin) {
    this.admin = admin;
    return this;
  }

  public List<String> getRoles() {
    return roles;
  }

  public User setRoles(List<String> roles) {
    this.roles = roles;
    return this;
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

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    if (admin) {
      return Arrays.asList(new SimpleGrantedAuthority("ROLE_USER"),
          new SimpleGrantedAuthority("ROLE_ADMIN"));
    } else {
      return Arrays.asList(new SimpleGrantedAuthority("ROLE_USER"));
    }
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    User user = (User) o;
    return id == user.id &&
        username.equals(user.username) &&
        password.equals(user.password) &&
        email.equals(user.email);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, username, password, email);
  }
}