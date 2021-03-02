package bg.codeacademy.spring.gossiptalks.conttroler;

import bg.codeacademy.spring.gossiptalks.service.UserService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
@Validated
public class UserController {

  private UserService userService;

  public UserController(UserService userService) {
    this.userService = userService;
  }
}
