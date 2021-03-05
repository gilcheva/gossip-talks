package bg.codeacademy.spring.gossiptalks.model;

import java.time.OffsetDateTime;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;


@Entity
public class Gossip {

  @Id
  @Pattern(regexp = "[A-Z0-9]+")
  private String id;
  @NotNull
  @Size(min = 2, max = 255)
  @Pattern(regexp = "<[^a-zA-Z\\/][^>]*>")
  private String text;
  @NotNull
  private String username;
  @NotNull
  private OffsetDateTime dateTime;

  public String getText() {
    return text;
  }

  public Gossip setText(String text) {
    this.text = text;
    return this;
  }

  public String getUsername() {
    return username;
  }

  public Gossip setUsername(String username) {
    this.username = username;
    return this;
  }

  public String getId() {
    return id;
  }


  public Gossip setId(String id) {
    this.id = id;
    return this;
  }

  public OffsetDateTime getDateTime() {
    return dateTime;
  }

  public Gossip setDateTime(OffsetDateTime dateTime) {
    this.dateTime = dateTime;
    return this;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Gossip gossip = (Gossip) o;
    return id == gossip.id &&
        username.equals(gossip.username) &&
        text.equals(gossip.text) &&
        dateTime.equals(gossip.dateTime);
  }

  @Override
  public int hashCode() {//TODO
    return Objects.hash(text, username, id, dateTime);
  }
}
