package bg.codeacademy.spring.gossiptalks.conttroler;


import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/v1/gossip")
@Validated
public class GossipController {

}
