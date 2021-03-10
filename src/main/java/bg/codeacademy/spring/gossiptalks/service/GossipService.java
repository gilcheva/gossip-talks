package bg.codeacademy.spring.gossiptalks.service;

import bg.codeacademy.spring.gossiptalks.dto.GossipList;
import bg.codeacademy.spring.gossiptalks.model.Gossip;
import bg.codeacademy.spring.gossiptalks.model.User;
import bg.codeacademy.spring.gossiptalks.repository.GossipRepository;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;


@Service
public class GossipService {

  private final GossipRepository gossipRepository;

  public GossipService(GossipRepository gossipRepository) {
    this.gossipRepository = gossipRepository;
  }
  public GossipList getGossips(Integer pageNo, Integer pageSize, User user) {
    Set<User> friendList = user.getFollowers();
    GossipList gossips = new GossipList();
    ArrayList<Gossip>content = new ArrayList<Gossip>();
    DateTimeFormatter fm = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    if(!friendList.isEmpty()){
      Iterator<User> iterator = friendList.iterator();
      while (iterator.hasNext()) {
       User myFriend = iterator.next();
       long idFriend = myFriend.getId();
       List<Gossip>gossipList = gossipRepository.findByAuthor_Id(idFriend);
       if(!gossipList.isEmpty()){
         content.addAll(gossipList);

       }
      }
    }
    int total = content.size();
    content = (ArrayList<Gossip>) content.stream()
        .distinct()
        .sorted((Gossip g1, Gossip g2) -> g2.getDateTime().format(fm)
            .compareTo(g1.getDateTime().format(fm)))
        .limit(pageSize)
        .collect(Collectors.toList());
    int count = content.size();
    gossips.setContent(content);
    gossips.setCount(count);
    gossips.setTotal(total);
    gossips.setPageSize(pageSize);
    gossips.setPageNumber(pageNo);

    return gossips;
  }

  public Gossip postGossip(String text, User user) {
    OffsetDateTime dateTime = OffsetDateTime.now();

    Gossip gossip = new Gossip();
    gossip.setAuthor(user);
    gossip.setText(text);
    gossip.setDateTime(dateTime);
    gossipRepository.save(gossip);

    //return gossipRepository.findByText(text);
    List<Gossip> gossipList = gossipRepository.findByAuthor_Id(user.getId());
    int index = gossipList.size();
    return gossipList.get(index-1);


  }

}
