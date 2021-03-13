package bg.codeacademy.spring.gossiptalks.service;

import static bg.codeacademy.spring.gossiptalks.service.UserServiceTest.newUser;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import bg.codeacademy.spring.gossiptalks.model.Gossip;
import bg.codeacademy.spring.gossiptalks.model.User;
import bg.codeacademy.spring.gossiptalks.repository.GossipRepository;
import bg.codeacademy.spring.gossiptalks.repository.UserRepository;
import java.security.Principal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

class GossipServiceTest {

  private GossipService gossipService;
  @Mock
  private GossipRepository gossipRepository;
  @Mock
  private UserRepository userRepository;
  @Mock
  Principal mockPrincipal;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.initMocks(this);
    gossipService = new GossipService(userRepository, gossipRepository);
//    int pageNumber = 0;
//    int pageSize = 1;
//    Pageable pageable = PageRequest.of(pageNumber, pageSize);
  }

  @Test
  void Given_username_When_getGossipsByAuthor_called_Then_page_returned() {
    int pageNumber = 0;
    int pageSize = 1;
    User author = newUser("user1@abv.bg");
    Gossip gossip = new Gossip();
//    gossip.setAuthor(author);
    Page<Gossip> expectedPage = new PageImpl<>(Collections.singletonList(gossip));

    when(gossipRepository.findByAuthor_Username(anyString(), any(Pageable.class)))
        .thenReturn(expectedPage);
    Page<Gossip> returnedPage = gossipService
        .getGossipsByAuthor(pageNumber, pageSize, author.getUsername());

    assertEquals(returnedPage.getNumberOfElements(), 1);
    assertSame(returnedPage, expectedPage);
  }

  @Test
  void Given_not_existing_username_When_getGossipsByAuthor_called_Then_message_returned() {
    int pageNumber = 0;
    int pageSize = 1;
    User author = newUser("user1@abv.bg");
    Gossip gossip = new Gossip();
    Page<Gossip> expectedPage = new PageImpl<>(Collections.singletonList(gossip));

    when(gossipRepository.findByAuthor_Username(anyString(), any(Pageable.class)))
        .thenReturn(expectedPage);
    Page<Gossip> returnedPage = gossipService
        .getGossipsByAuthor(pageNumber, pageSize, author.getUsername());

    assertEquals(returnedPage.getNumberOfElements(), 1);
    assertSame(returnedPage, expectedPage);
  }

  @Test
  public void Given_no_gossips_When_getGossipsByAuthor_called_Then_should_return_EmptyList() {
    int pageNumber = 0;
    int pageSize = 1;
    User author = newUser("user1@abv.bg");
    List<Gossip> gossips = new ArrayList<>();
    Page<Gossip> expectedPage = new PageImpl<>(gossips);

    when(gossipRepository.findByAuthor_Username(anyString(), any(Pageable.class)))
        .thenReturn(expectedPage);
    Page<Gossip> returnedPage = gossipService
        .getGossipsByAuthor(pageNumber, pageSize, author.getUsername());

    assertTrue(returnedPage.isEmpty());
  }

//    gossipService.getGossipsByAuthor(anyString());
//  }

//  @Test
//  void Given_no_logged_user_When_getGossipsByAuthor_called_Then_should_throw_exception() {
//    User author = newUser("user1@abv.bg");
//    int pageNumber = 0;
//    int pageSize = 1;
//
//  assertThrows(UsernameNotFoundException.class, () -> {
//    gossipService.getGossipsByAuthor(pageNumber, pageSize, author.getUsername());
//  });
//  }

  @Test
  public void When_getGossipsByAuthor_called_Then_should_return_AllGossips() {
    int pageNumber = 0;
    int pageSize = 2;
    User currentUser = newUser("user1@abv.bg");
    Gossip gossip1 = new Gossip();
    Gossip gossip2 = new Gossip();
    Page<Gossip> expectedPage = new PageImpl<>(Arrays.asList(gossip1, gossip2));

    when(gossipRepository.findByAuthor_Username(anyString(), any(Pageable.class)))
        .thenReturn(expectedPage);
    Page<Gossip> returnedPage = gossipService
        .getGossipsByAuthor(pageNumber, pageSize, currentUser.getUsername());

    assertEquals(2, returnedPage.getSize());
  }

  @Test
  void Given_valid_user_When_getGossips_called_Then_page_returned() {
    int pageNumber = 0;
    int pageSize = 1;
    User currentUser = newUser("user1@abv.bg");
    List<User> friends = new ArrayList<>();
    Gossip gossip = new Gossip();
    Page<Gossip> expectedPage = new PageImpl<>(Collections.singletonList(gossip));

    when(userRepository.findByFollowers_Id(anyInt())).thenReturn(friends);
    when(gossipRepository.findByAuthorIn(anyList(), any(Pageable.class)))
        .thenReturn(expectedPage);
    Page<Gossip> returnedPage = gossipService
        .getGossips(pageNumber, pageSize, currentUser);

    assertEquals(returnedPage.getNumberOfElements(), 1);
    assertSame(returnedPage, expectedPage);
  }

  @Test
  public void Given_no_friends_When_getGossips_called_Then_should_return_EmptyList() {
    int pageNumber = 0;
    int pageSize = 1;
    User currentUser = newUser("user1@abv.bg");
    List<User> friends = new ArrayList<>();
    Page<Gossip> expectedPage = new PageImpl<>(Collections.emptyList());

    when(userRepository.findByFollowers_Id(anyInt())).thenReturn(friends);
    when(gossipRepository.findByAuthorIn(anyList(), any(Pageable.class)))
        .thenReturn(expectedPage);
    Page<Gossip> returnedPage = gossipService.getGossips(pageNumber, pageSize, currentUser);

    assertTrue(returnedPage.isEmpty());
  }

  @Test
  public void When_getGossips_called_Then_should_return_AllGossips() {
    int pageNumber = 0;
    int pageSize = 2;
    User currentUser = newUser("user1@abv.bg");
    List<User> friends = new ArrayList<>();
    Gossip gossip1 = new Gossip();
    Gossip gossip2 = new Gossip();
    Page<Gossip> expectedPage = new PageImpl<>(Arrays.asList(gossip1, gossip2));

    when(userRepository.findByFollowers_Id(anyInt())).thenReturn(friends);
    when(gossipRepository.findByAuthorIn(anyList(), any(Pageable.class)))
        .thenReturn(expectedPage);
    Page<Gossip> returnedPage = gossipService.getGossips(pageNumber, pageSize, currentUser);

    assertEquals(2, returnedPage.getSize());
  }

  //getGossips should return unautorized when user not logged
  //sorted gossips

//  @Test
//  public void When_createGossip_called_Then_add_in_repository() {
//    User currentUser = newUser("user1@abv.bg");
//    Gossip gossip = new Gossip();
//    gossip.setText("1").setAuthor(currentUser);
//    when(gossipRepository.findByAuthor_Id(anyLong())).thenReturn(Collections.singletonList(gossip));
//
//    gossipService.createGossip("1", currentUser);
//
//    verify(gossipRepository, times(2)).save(gossip);
//  }

  //html entities forbidden in createGossip
}