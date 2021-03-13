package bg.codeacademy.spring.gossiptalks.service;

import bg.codeacademy.spring.gossiptalks.model.User;
import bg.codeacademy.spring.gossiptalks.repository.GossipRepository;
import bg.codeacademy.spring.gossiptalks.repository.UserRepository;
import java.time.OffsetDateTime;
import java.util.HashSet;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


class UserServiceTest {


  @Mock
  private UserRepository userRepository;
  @Mock
  private GossipRepository gossipRepository;

  private UserService userService;

  @BeforeEach
  public void setup() {
    MockitoAnnotations.initMocks(this);
    userService = new UserService(userRepository, gossipRepository,
        NoOpPasswordEncoder.getInstance());

  }

  @AfterEach
  void tearDown() {

  }


  public static User newUser(String email) {
    String name = email.split("@")[0];
    return new User().setEmail(email)
        .setUsername(name)
        .setName(name)
        .setId(1)
        .setRegistrationTime(OffsetDateTime.now())
        .setLastLoginTime(OffsetDateTime.now())
        .setPassword(name)
        .setFollowers(new HashSet<User>());
  }

  // Test follow
  // - user exists, test remove
  // - user doesn't exist
  @Test
  void when_user_exist_Then_unfollow_user_Must_succeed() {

  }

  @Test
  void when_user_do_not_exist_Then_follow_user_Must_throw_exception() {
    // 1.setup current user
    User current = newUser("user1@abv.bg");

    // repository is not setup, so any username will return null (not found)
    // when not found - assert exception is thrown
    assertThrows(UsernameNotFoundException.class, () -> {
      userService.followUser(current, "alabal", true);
    });
  }

  @Test
  void when_user_exist_Then_follow_user_Must_succeed() {
    // 1.setup current user
    User current = newUser("user1@abv.bg");
    // 2.setup user to follow
    User toFollow = newUser("toFollow@abv.bg");
    // 3. setup db mock to return 1 & 2
    // казвам на мокнатото repository какво да прави
    // когато се викне findByUser трябва да върне toFollow
    when(userRepository.findByUsername(any()))
        .thenReturn(toFollow);
    // 4. call follow
    userService.followUser(current, "toFollow", true);
    // 5. validate current.user.followers contains user 2
    assertTrue(current.getFollowers().contains(toFollow));
    // 6. validate that db.save() is called
    // validate that save is called exactly 1 times
    // validate that save is called with parameter 'current'
    verify(userRepository, times(1)).save(current);
  }
}