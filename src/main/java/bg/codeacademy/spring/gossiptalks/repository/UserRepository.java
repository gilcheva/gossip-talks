package bg.codeacademy.spring.gossiptalks.repository;

import bg.codeacademy.spring.gossiptalks.model.User;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface UserRepository extends JpaRepository<User, Long> {

  User findByUsername(String username);
  List<User> findAll();
  List<User> findByUsernameContainsIgnoreCase(String username);
  List<User> findByNameContainsIgnoreCase(String name);
  User findByName(String name);
  //List<User> findAllByFollowers();

}
