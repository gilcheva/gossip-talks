package bg.codeacademy.spring.gossiptalks.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class GossipDto {

  private String text;
  @Pattern(regexp = "^[a-z0-8\\.\\-]+$")
  @NotNull
  private String username;
  @Pattern(regexp = "[A-Z0-9]+")
  @NotNull
  private String id;
  @NotNull
  private String dateTime;


  public String getText() {
    return text;
  }

  public GossipDto setText(String text) {
    this.text = text;
    return this;
  }
  public String getUsername() {
    return username;
  }

  public GossipDto setUsername(String username) {
    this.username = username;
    return this;
  }

  public String getId() {
    return id;
  }

  public GossipDto setId(String id) {
    this.id = id;
    return this;
  }

  public String getDateTime() {
    return dateTime;
  }

  public GossipDto setDateTime(String dateTime) {
    this.dateTime = dateTime;
    return this;
  }
}

