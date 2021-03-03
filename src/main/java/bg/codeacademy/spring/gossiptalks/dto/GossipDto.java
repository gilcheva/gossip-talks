package bg.codeacademy.spring.gossiptalks.dto;

import com.sun.istack.NotNull;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

public class GossipDto {
  // A 32-radix representation of the gossip ID.pattern: '[A-Z0-9]+'
  @NotNull
  private String id;

//max 255 - A common-mark formatted text. For safety reasons HTML entities MUST be forbidden.)
  private String text;

  @NotBlank
  @Pattern(regexp = "^[a-z0-8\\.\\-]+$")
  private String username;

  //format: date-time
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

