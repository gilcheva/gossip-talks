package bg.codeacademy.spring.gossiptalks.service;

import bg.codeacademy.spring.gossiptalks.dto.GossipList;
import bg.codeacademy.spring.gossiptalks.model.Gossip;
import bg.codeacademy.spring.gossiptalks.model.User;
import bg.codeacademy.spring.gossiptalks.repository.GossipRepository;
import bg.codeacademy.spring.gossiptalks.repository.UserRepository;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
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


  public User changePassword(String newPassword, String passwordConfirmation,
      String oldPassword) {

    if (!passwordConfirmation.equals(newPassword)) {
      throw new IllegalArgumentException("The password doesn't match");
    }
    if (newPassword.equals(oldPassword)) {
      throw new IllegalArgumentException("The passwords are the same");
    }
    //set new password and save
    User user = getCurrentUser();
    if (user == null) {
      throw new UsernameNotFoundException("The user have to loggin");
    }
    user.setPassword(passwordEncoder.encode(newPassword));
    userRepository.save(user);
    return user;
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
            (gossipRepository.findByAuthor_Id(u1.getId())).size() <
                (gossipRepository.findByAuthor_Id(u2.getId())).size() ?
                -1 : 1)
        .skip(pageNumber * pageSize)
        .limit(pageSize)
        .collect(Collectors.toList());
  }



  public User getCurrentUser() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String username = authentication.getName();
    if (!(authentication instanceof AnonymousAuthenticationToken)) {
      username = authentication.getName();
     }
    return userRepository.findByUsername(username);
  }

  public GossipList getUserGossips(Integer pageNo, Integer pageSize, String username) {
      List<Gossip> gossipList = new ArrayList<Gossip>();
      long userId = userRepository.findByUsername(username).getId();
      //gossipRepository.findByAuthor_Username(username);
      DateTimeFormatter fm = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

      gossipList =  gossipRepository.findByAuthor_Id(userId);
      int total = gossipList.size();
      gossipList = gossipList.stream()
        .distinct()
        .sorted((Gossip g1, Gossip g2) -> g2.getDateTime().format(fm)
            .compareTo(g1.getDateTime().format(fm)))
        .skip(pageNo * pageSize)
        .limit(pageSize)
        .collect(Collectors.toList());
      int count = gossipList.size();
    GossipList gossip = new GossipList();
    gossip.setContent((ArrayList<Gossip>) gossipList);
    gossip.setPageNumber(pageNo);
    gossip.setPageSize(pageSize);
    gossip.setTotal(total);
    gossip.setCount(count);
    return gossip;


  }


}
