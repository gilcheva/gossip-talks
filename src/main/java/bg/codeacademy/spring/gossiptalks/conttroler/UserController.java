package bg.codeacademy.spring.gossiptalks.conttroler;


import bg.codeacademy.spring.gossiptalks.dto.ChangePasswordRequest;
import bg.codeacademy.spring.gossiptalks.dto.CreateUserRequest;
import bg.codeacademy.spring.gossiptalks.model.User;
import bg.codeacademy.spring.gossiptalks.service.UserService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import javax.validation.Valid;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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



  //Valio-----UserResponse changeCurrentUserPassword(@ApiParam(value = "", required=true) @RequestPart(value="password", required=true)  String password,@ApiParam(value = "", required=true) @RequestPart(value="passwordConfirmation", required=true)  String passwordConfirmation,@ApiParam(value = "", required=true) @RequestPart(value="oldPassword", required=true)  String oldPassword);*/
  //Kitaecl---public User registerUser(@ApiParam(value = "", required=true) @RequestPart(value="username", required=true)  createUserRequest.getUsername(),) {
/*  @PostMapping
    public User registerUserNew(
      @ApiParam(value = "", required = true) @RequestBody CreateUserRequest createUserRequest) {
    return userService.register(createUserRequest.getUsername(), createUserRequest.getPassword(),
        createUserRequest.getPasswordConfirmation(), createUserRequest.getEmail(),
        createUserRequest.getName(), createUserRequest.isFollowing());
  }
*/

  @PostMapping
  public User registerUserOld(@RequestBody CreateUserRequest createUserRequest) {
    return userService.register(createUserRequest.getUsername(), createUserRequest.getPassword(),
        createUserRequest.getPasswordConfirmation(), createUserRequest.getEmail(),
        createUserRequest.getName(), createUserRequest.isFollowing());
  }

  @PostMapping("/{userID}")
  public User updatePassword(@PathVariable("userID") int userID,
      @RequestBody ChangePasswordRequest request) {
    return userService.changePassword(userID, request.getOldPassword(), request.getNewPassword());
  }

//  @GetMapping
//  public List<UserResponse> getUsers(@RequestParam(required = false) String name,
//      @RequestParam(required = false) boolean following) {
//
//    Page<User> users = userService.listUsers(PageRequest.of(0, 20), name, following);
//
//    return users.stream().map(user -> new UserResponse()
//        .setId(user.getId())
//        .setUsername(user.getUsername())
//        .setName(user.getName())
//        .setFollowing(user.isFollowing())
////TODO    ).sorted(u->u.getGossips)
//    ) .collect(Collectors.toList());
//
//  }

}
