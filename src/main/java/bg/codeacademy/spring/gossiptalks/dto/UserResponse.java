package bg.codeacademy.spring.gossiptalks.dto;

public class UserResponse {

  private String email;
  private String username;
  private String name;
  private boolean following;

  public String getEmail() {
    return email;
  }

  public UserResponse setEmail(String email) {
    this.email = email;
    return this;
  }
  public String getUsername() {
    return username;
  }

  public UserResponse setUsername(String username) {
    this.username = username;
    return this;
  }

  public String getName() {
    return name;
  }

  public UserResponse setName(String name) {
    this.name = name;
    return this;
  }

  public boolean isFollowing() {
    return following;
  }

  public UserResponse setFollowing(boolean following) {
    this.following = following;
    return this;
  }
}

