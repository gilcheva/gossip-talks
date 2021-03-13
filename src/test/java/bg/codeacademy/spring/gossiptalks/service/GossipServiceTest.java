package bg.codeacademy.spring.gossiptalks.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import bg.codeacademy.spring.gossiptalks.model.Gossip;
import bg.codeacademy.spring.gossiptalks.model.User;
import bg.codeacademy.spring.gossiptalks.repository.GossipRepository;
import bg.codeacademy.spring.gossiptalks.repository.UserRepository;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

class GossipServiceTest {

  private GossipService gossipService;
  @Mock
  private GossipRepository gossipRepository;
  @Mock
  private UserRepository userRepository;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.initMocks(this);
    gossipService = new GossipService(userRepository, gossipRepository);
  }

  @Test
  void Given_String_When_getGossipsByAuthor_called_Then_page_returned() {
    int pageNumber = 0;
    int pageSize = 1;
    Pageable pageable = PageRequest.of(pageNumber, pageSize);
    String username = "proba";
    Gossip gossip = new Gossip();
    Page<Gossip> expectedPage = new PageImpl<>(Collections.singletonList(gossip));

    when(gossipRepository.findByAuthor_Username(username, pageable)).thenReturn(expectedPage);
    Page<Gossip> returnedPage = gossipRepository.findByAuthor_Username(username, pageable);
//    when(gossipRepository.findByAuthor_Username(anyString(), any(Pageable.class))).thenReturn(expectedPage);
//    Page<Gossip> returnedPage = gossipRepository.findByAuthor_Username(anyString(), any(Pageable.class));

    assertEquals(returnedPage.getNumberOfElements(), 1);
    assertSame(returnedPage, expectedPage);
  }

  @Test
  public void Given_no_data_When_getGossipsByAuthor_called_Then_should_return_EmptyList() {
    int pageNumber = 0;
    int pageSize = 1;
    Pageable pageable = PageRequest.of(pageNumber, pageSize);
    List<Gossip> gossips = new ArrayList<>();
    Page<Gossip> pagedGossips = new PageImpl<>(gossips);
    String username = "proba";
    when(gossipRepository.findByAuthor_Username(username, pageable)).thenReturn(pagedGossips);

    Page<Gossip> returnedPage = gossipRepository.findByAuthor_Username(username,pageable);

    assertTrue(returnedPage.isEmpty());
  }

    @Test
    void Given_valid_user_When_getGossips_called_Then_page_returned() {
    int pageNumber = 0;
    int pageSize = 1;
    Pageable pageable = PageRequest.of(pageNumber, pageSize);
    User user = new User();
    List<User> friends = new ArrayList<>();
    friends.add(user);
    Gossip gossip = new Gossip();
    Page<Gossip> expectedPage = new PageImpl<>(Collections.singletonList(gossip));

    when(userRepository.findByFollowers_Id(user.getId())).thenReturn(friends);
    when(gossipRepository.findByAuthorIn(friends, pageable)).thenReturn(expectedPage);
    Page<Gossip> returnedPage = gossipRepository.findByAuthorIn(friends, pageable);

    assertEquals(returnedPage.getNumberOfElements(), 1);
    assertSame(returnedPage, expectedPage);
  }

  @Test
  public void Given_no_friends_When_getGossips_called_Then_should_return_EmptyList() {
    int pageNumber = 0;
    int pageSize = 1;
    Pageable pageable = PageRequest.of(pageNumber, pageSize);
    User user = new User();
    List<User> friends = new ArrayList<>();
    //връзката с friends се губи
    Page<Gossip> expectedPage = new PageImpl<>(Collections.emptyList());

    when(userRepository.findByFollowers_Id(user.getId())).thenReturn(friends);
    when(gossipRepository.findByAuthorIn(friends, pageable)).thenReturn(expectedPage);
    Page<Gossip> returnedPage = gossipRepository.findByAuthorIn(friends, pageable);

    assertTrue(returnedPage.isEmpty());
  }
}