package bg.codeacademy.spring.gossiptalks.service;

import bg.codeacademy.spring.gossiptalks.dto.UserResponse;
import bg.codeacademy.spring.gossiptalks.model.User;
import bg.codeacademy.spring.gossiptalks.repository.GossipRepository;
import bg.codeacademy.spring.gossiptalks.repository.UserRepository;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class UserService implements UserDetailsService {

  private final UserRepository userRepository;
  private final GossipRepository gossipRepository;
  private final PasswordEncoder passwordEncoder;

  public UserService(UserRepository userRepository,
      GossipRepository gossipRepository,
      PasswordEncoder passwordEncoder) {
    this.userRepository = userRepository;
    this.gossipRepository = gossipRepository;
    this.passwordEncoder = passwordEncoder;
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    return userRepository.findByUsername(username);
  }

  public User register(String userName,
      String userPassword,
      String passConfirmation,
      String userEmail,
      String name,
      boolean following) {
    if (!userPassword.equals(passConfirmation)) {
      throw new IllegalArgumentException("The password doesn't match");
    }
    if (userRepository.findByUsername(userName) != null) {
      throw new IllegalArgumentException("The username already exist");
    }
    User user = new User();
    user.setUsername(userName);
    user.setPassword(passwordEncoder.encode(userPassword));
    user.setRegistrationTime(OffsetDateTime.now());
    user.setLastLoginTime(OffsetDateTime.now());
    user.setEmail(userEmail);
    user.setName(name);
    return userRepository.save(user);
  }


  public UserResponse changePassword(String newPassword, String passwordConfirmation, String  oldPassword) {

    if (!passwordConfirmation.equals(newPassword))
      throw new IllegalArgumentException("The password doesn't match");
    if (newPassword.equals(oldPassword)) {
      throw new IllegalArgumentException("The passwords are the same");
    }
    //set new password and save
    User user = getCurrentUser();
    if(user==null){
      throw new UsernameNotFoundException("The user have to loggin");
    }
    user.setPassword(passwordEncoder.encode(newPassword));
    userRepository.save(user);
    UserResponse userResponse = new UserResponse();
    userResponse.setName(user.getName());
    userResponse.setUsername(user.getUsername());
    userResponse.setEmail(user.getEmail());
    return userResponse;
  }

  public User followUser(User currentUser, String username, boolean follow) {
    // make sure if the user exist
    User user = userRepository.findByUsername(username);
    if (user == null) {
      throw new UsernameNotFoundException("The user not found");
    }
    if (follow) {
      //add User to Followers
      currentUser.getFollowers().add(user);

    } else {
      //remove from Followers
      currentUser.getFollowers().remove(user);
    }
    return userRepository.save(currentUser);

  }

  public List<User> getUsers() {
    return getUsers("", false);
  }

  public List<User> getUsers(String name, boolean f) {
    List<User> userList = new ArrayList<>();
    int pageNumber = 0;
    int pageSize = 20;
    if (!f) {
      userList = userRepository.findByUsernameContainsIgnoreCase(name);
      userList.addAll(userRepository.findByNameContainsIgnoreCase(name));
    } else {
      if (name != null) {
        User currentUser = getCurrentUser();
        userList = currentUser.getFollowers().stream()
            .filter(user -> (user.getUsername().toUpperCase().contains(name.toUpperCase())))
            .collect(Collectors.toList());
        userList.addAll(currentUser.getFollowers().stream()
            .filter(user -> (user.getName().toUpperCase().contains(name.toUpperCase())))
            .collect(Collectors.toList()));
      }
    }
    return userList.stream()
        .distinct()
        .sorted((User u1, User u2) ->
            (gossipRepository.findByUsername(u1.getUsername())).size() <
                (gossipRepository.findByUsername(u2.getUsername())).size() ?
                -1 : 1)
        .skip(pageNumber * pageSize)
        .limit(pageSize)
        .collect(Collectors.toList());
  }

  public User getCurrentUser() {
    Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    String username;
    if (principal instanceof UserDetails) {
      username = ((UserDetails) principal).getUsername();
    } else {
      username = principal.toString();
    }
    return userRepository.findByUsername(username);
  }


}
