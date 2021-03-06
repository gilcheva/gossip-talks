package bg.codeacademy.spring.gossiptalks.conttroler;


import bg.codeacademy.spring.gossiptalks.model.User;
import bg.codeacademy.spring.gossiptalks.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/v1/users")
public class UserController {

  private UserService userService;

  public UserController(UserService userService) {
    this.userService = userService;
  }

  @PostMapping(consumes = {"multipart/form-data"})
  public void createUser(
      @RequestPart(value = "password", required = true) String password,
      @RequestPart(value = "passwordConfirmation", required = true) String passwordConfirmation,
      @RequestPart(value = "email", required = true) String email,
      @RequestPart(value = "username", required = true) String username,
      @RequestPart(value = "name", required = false) String name) {
    userService.register(username, password,
        passwordConfirmation, email,
        name, false);
  }

  /*@PostMapping("/{userID}")
  public User updatePassword(@PathVariable("userID") int userID,
      @RequestBody ChangePasswordRequest request) {
    return userService.changePassword(userID, request.getOldPassword(), request.getNewPassword());
  }
  */
  @PostMapping(consumes = {"multipart/form-data"}, path={"me/{userID}"})
  public User updatePassword(@PathVariable(value = "userID") long userID,
      @RequestPart(value = "password", required = true) String oldPassword,
      @RequestPart(value = "password", required = true) String newPassword) {
    return userService.changePassword(userID, oldPassword, newPassword);
  }

  @GetMapping("/me")
  public User currentUser() {
    return userService.getCurrentUser();
  }
}

