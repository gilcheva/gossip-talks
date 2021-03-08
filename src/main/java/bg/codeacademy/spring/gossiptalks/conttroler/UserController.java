package bg.codeacademy.spring.gossiptalks.conttroler;


import bg.codeacademy.spring.gossiptalks.dto.UserResponse;
import bg.codeacademy.spring.gossiptalks.model.User;
import bg.codeacademy.spring.gossiptalks.service.UserService;
import io.swagger.annotations.ApiParam;
import java.util.ArrayList;
import java.util.List;
import com.sun.istack.NotNull;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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

  
  @PostMapping(consumes = {"multipart/form-data"}, value={"/{username}/follow"})
  String followUser(
      @ApiParam(value = "" ,required = true) @PathVariable ("username") String username,
      @ApiParam(value = "" ,required = true )@RequestParam("follow") boolean follow
  ){
    User currentUser = userService.getCurrentUser();
    userService.followUser(currentUser,username,follow);
    return "OK";
  }


  @PostMapping(consumes = {"multipart/form-data"}, value={"/me"})

  public UserResponse changeCurrentUserPassword(
      @RequestPart(value = "password", required = true) String password,
      @RequestPart(value = "passwordConfirmation", required = true) String passwordConfirmation,
      @RequestPart(value = "oldPassword", required = true) String oldPassword) {
    return userService.changePassword(password, passwordConfirmation, oldPassword);



  }

  @GetMapping("/me")
  public User currentUser() {
    return userService.getCurrentUser();
  }


  @GetMapping
  public UserResponse[] getUsers(@NotNull @RequestParam(value ="name", required = false) String name,
      @RequestParam(name = "f", required = false, defaultValue = "false") boolean f) {

    List<User> users;
    if (name == null) {
      users = userService.getUsers();
    } else {
      users = userService.getUsers(name, f);
    }
    Stream<User> streamedUsers = users.stream();

    return streamedUsers.map(user -> new UserResponse()
        .setId(user.getId())
        .setEmail(user.getEmail())
        .setUsername(user.getUsername())
        .setName(user.getName())
    ).toArray(UserResponse[]::new);
  }
}

