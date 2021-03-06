package bg.codeacademy.spring.gossiptalks.dto;

public class GossipDto {

  private String text;
  private String username;
  private String id;
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

