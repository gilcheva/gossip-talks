package bg.codeacademy.spring.gossiptalks.conttroler;

import bg.codeacademy.spring.gossiptalks.dto.GossipList;
import bg.codeacademy.spring.gossiptalks.dto.UserDto;
import bg.codeacademy.spring.gossiptalks.dto.UserResponse;
import bg.codeacademy.spring.gossiptalks.model.Gossip;
import bg.codeacademy.spring.gossiptalks.model.User;
import bg.codeacademy.spring.gossiptalks.service.GossipService;
import bg.codeacademy.spring.gossiptalks.service.UserService;
import io.swagger.annotations.ApiParam;
import java.util.List;
import java.util.stream.Stream;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import org.springframework.data.domain.Page;
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
  private final GossipService gossipService;

  public UserController(UserService userService,
      GossipService gossipService) {
    this.userService = userService;
    this.gossipService = gossipService;
  }

  @PostMapping(consumes = {"multipart/form-data"})
  public UserDto createUser(
      @RequestPart(value = "password", required = true) String password,
      @RequestPart(value = "passwordConfirmation", required = true) String passwordConfirmation,
      @RequestPart(value = "email", required = true) String email,
      @RequestPart(value = "username", required = true) String username,
      @RequestPart(value = "name", required = false) String name) {
    User newUser = userService.register(username, password,
        passwordConfirmation, email,
        name, false);
    return toDTOUSER(newUser);
  }


  @GetMapping
  public UserResponse[] getUsers(
      @RequestParam(value = "name", required = false) String name,
      @RequestParam(name = "f", required = false, defaultValue = "false") boolean f) {

    List<User> users = userService.getUsers(name, f);
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

    return toDTO(currentUser).setFollowing(follow);

  }

  @PostMapping(consumes = {"multipart/form-data"}, value = {"/me"})
  public UserResponse changeCurrentUserPassword(
      @RequestPart(value = "password", required = true) String password,
      @RequestPart(value = "passwordConfirmation", required = true) String passwordConfirmation,
      @RequestPart(value = "oldPassword", required = true) String oldPassword) {
    User currentUser = userService.changePassword(password, passwordConfirmation, oldPassword);
    return toDTO(currentUser);

  }

  @GetMapping("/me")
  public UserResponse currentUser() {
    User currentUser = userService.getCurrentUser();
    return toDTO(currentUser);

  }

  @GetMapping("/{username}/gossips")
  public GossipList getUserGossips(
      @Min(0) @RequestParam(value = "pageNo", required = false, defaultValue = "0") Integer pageNo,
      @Min(0) @Max(50) @RequestParam(value = "pageSize", required = false, defaultValue = "0") Integer pageSize,
      @PathVariable(value = "username", required = true) String username
  ) {
    Page<Gossip> gossips = gossipService.getGossipsByAuthor(pageNo, pageSize, username);
    return GossipController.toDTO(gossips);
  }

  static UserResponse toDTO(User user) {

    return new UserResponse()
        .setEmail(user.getEmail())
        .setName(user.getName())
        .setFollowing(false)
        .setUsername(user.getUsername()
        );

  }

  static UserDto toDTOUSER(User user) {
    return new UserDto()
        .setEmail(user.getEmail())
        .setFollowing(false)
        .setName(user.getName())
        .setUsername(user.getUsername())
        .setPassword(user.getPassword())
        .setLastLoginTime(user.getLastLoginTime())
        .setRegistrationTime(user.getRegistrationTime()
        );


  }
}


