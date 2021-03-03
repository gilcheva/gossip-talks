package bg.codeacademy.spring.gossiptalks.conttroler;

import bg.codeacademy.spring.gossiptalks.dto.UserResponse;
import bg.codeacademy.spring.gossiptalks.model.User;
import bg.codeacademy.spring.gossiptalks.service.UserService;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.PositiveOrZero;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
@Validated
public class UserController {

  private UserService userService;

  public UserController(UserService userService) {
    this.userService = userService;
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
