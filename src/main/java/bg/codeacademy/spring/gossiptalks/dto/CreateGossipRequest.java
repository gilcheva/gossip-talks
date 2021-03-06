package bg.codeacademy.spring.gossiptalks.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class CreateGossipRequest {

  @NotNull
  @Size(min = 2, max = 255)
  @Pattern(regexp = "<[^a-zA-Z\\/][^>]*>")
  private String text;

  public String getText() {
    return text;
  }
  public CreateGossipRequest setText(String text) {
    this.text = text;
    return this;
  }
}

