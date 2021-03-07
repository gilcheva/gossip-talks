package bg.codeacademy.spring.gossiptalks.service;

import bg.codeacademy.spring.gossiptalks.model.Gossip;
import bg.codeacademy.spring.gossiptalks.model.User;
import bg.codeacademy.spring.gossiptalks.repository.GossipRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class GossipService {

  private final GossipRepository gossipRepository;

  public GossipService(GossipRepository gossipRepository) {
    this.gossipRepository = gossipRepository;
  }

  public Page<Gossip> listGossips(Pageable pageable) {
      return gossipRepository.findAll(pageable);
  }
}
