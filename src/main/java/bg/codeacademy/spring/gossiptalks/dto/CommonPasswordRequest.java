package bg.codeacademy.spring.gossiptalks.dto;

import bg.codeacademy.spring.gossiptalks.validation.ValidPassword;
import javax.validation.constraints.NotNull;

public class CommonPasswordRequest {

  @NotNull
  @ValidPassword
  private String password;

  @NotNull
  @ValidPassword
  private String passwordConfirmation;

  public String getPassword() {
    return password;
  }

  public CommonPasswordRequest setPassword(String password) {
    this.password = password;
    return this;
  }

  public String getPasswordConfirmation() {
    return passwordConfirmation;
  }

  public CommonPasswordRequest setPasswordConfirmation(String passwordConfirmation) {
    this.passwordConfirmation = passwordConfirmation;
    return this;
  }


}
