package bg.codeacademy.spring.gossiptalks.conttroler;


import bg.codeacademy.spring.gossiptalks.dto.GossipList;
import bg.codeacademy.spring.gossiptalks.dto.UserResponse;
import bg.codeacademy.spring.gossiptalks.model.User;
import bg.codeacademy.spring.gossiptalks.service.UserService;
import io.swagger.annotations.ApiParam;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotNull;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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


  @GetMapping
  public UserResponse[] getUsers(
      @NotNull @RequestParam(value = "name", required = false) String name,
      @RequestParam(name = "f", required = false, defaultValue = "false") boolean f) {

    List<User> users;
    if (name == null) {
      users = userService.getUsers();
    } else {
      users = userService.getUsers(name, f);
    }
    Stream<User> streamedUsers = users.stream();

    return streamedUsers.map(user -> new UserResponse()
        .setEmail(user.getEmail())
        .setUsername(user.getUsername())
        .setName(user.getName())
        .setFollowing(f)
    ).toArray(UserResponse[]::new);
  }

  @PostMapping(consumes = {"multipart/form-data"}, value = {"/{username}/follow"})
  UserResponse followUser(
      @ApiParam(value = "", required = true) @PathVariable("username") String username,
      @ApiParam(value = "", required = true) @RequestParam("follow") boolean follow
  ) {
    User currentUser = userService.getCurrentUser();
    currentUser = userService.followUser(currentUser, username, follow);
    UserResponse userResponse = new UserResponse()
        .setEmail(currentUser.getEmail())
        .setUsername(currentUser.getUsername())
        .setName(currentUser.getName())
        .setFollowing(follow);

    return userResponse;

  }

  @PostMapping(consumes = {"multipart/form-data"}, value = {"/me"})

  public UserResponse changeCurrentUserPassword(
      @RequestPart(value = "password", required = true) String password,
      @RequestPart(value = "passwordConfirmation", required = true) String passwordConfirmation,
      @RequestPart(value = "oldPassword", required = true) String oldPassword) {
    User currentUser = userService.getCurrentUser();
    currentUser = userService.changePassword(password, passwordConfirmation, oldPassword);
    UserResponse userResponse = new UserResponse()
        .setEmail(currentUser.getEmail())
        .setUsername(currentUser.getUsername())
        .setName(currentUser.getName());

    return userResponse;
  }

  @GetMapping("/me")
  public UserResponse currentUser() {
    User currentUser = userService.getCurrentUser();
    UserResponse userResponse = new UserResponse()
        .setEmail(currentUser.getEmail())
        .setUsername(currentUser.getUsername())
        .setName(currentUser.getName());
    if(!currentUser.getFollowers().isEmpty()){
      userResponse.setFollowing(true);
    }
    return userResponse;
  }

  @GetMapping("/{username}/gossips")
  public GossipList getUserGossips(
      @RequestParam(value = "pageNo", required = false) Integer pageNo,
      @RequestParam(value = "pageSize", required = false) Integer pageSize,
      @PathVariable(value = "username", required = true) String username
  ) {
    return userService.getUserGossips(pageNo, pageSize, username);

  }
}
