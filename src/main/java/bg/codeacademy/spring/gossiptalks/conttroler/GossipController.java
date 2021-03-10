package bg.codeacademy.spring.gossiptalks.conttroler;


import bg.codeacademy.spring.gossiptalks.dto.CreateGossipRequest;
import bg.codeacademy.spring.gossiptalks.dto.GossipDto;
import bg.codeacademy.spring.gossiptalks.dto.GossipList;
import bg.codeacademy.spring.gossiptalks.model.Gossip;
import bg.codeacademy.spring.gossiptalks.model.User;
import bg.codeacademy.spring.gossiptalks.service.GossipService;
import bg.codeacademy.spring.gossiptalks.service.UserService;
import java.time.OffsetDateTime;
import org.apache.commons.codec.binary.Base32;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/v1/gossips")
@Validated
public class GossipController {
  private UserService userService;

  public GossipController(UserService userService,
      GossipService gossipService) {
    this.userService = userService;
    this.gossipService = gossipService;
  }

  private GossipService gossipService;

  @GetMapping
  public GossipList getGossips(
      @RequestParam(value = "pageNo", required = false) Integer pageNo,
      @RequestParam(value ="pageSize", required = false) Integer pageSize){
      User user = userService.getCurrentUser();
      return gossipService.getGossips(pageNo,pageSize, user);
  }
  @PostMapping(consumes = {"multipart/form-data"})
  public GossipDto postGossip(
      @RequestPart(value = "text", required = true) String text){
      User user = userService.getCurrentUser();
      Gossip gossip =  gossipService.postGossip(text, user);
      long id  = Long.parseLong(String.valueOf(gossip.getId()), 32);
      Base32 base32 = new Base32();
      String gossipId = base32.encodeToString(String.valueOf(id).getBytes());
      gossipId =gossipId.substring(0, gossipId.indexOf("="));
      GossipDto gossipDto = new GossipDto ();
      gossipDto.setText(text);
      gossipDto.setUsername(user.getUsername());
      gossipDto.setDateTime(OffsetDateTime.now().toString());
      gossipDto.setId(gossipId);
      return gossipDto;
  }

}
