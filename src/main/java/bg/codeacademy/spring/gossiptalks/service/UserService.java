package bg.codeacademy.spring.gossiptalks.service;

import bg.codeacademy.spring.gossiptalks.model.User;
import bg.codeacademy.spring.gossiptalks.repository.UserRepository;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class UserService implements UserDetailsService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
  }

   @Override
  public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
    return userRepository.findByUsername(s);
  }

  public User register(String userName, String userPassword, String passConfirmation,
      String userEmail,
      String name, boolean folowing) {
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

  public User changePassword(long userID, String oldPassword, String newPassword) {
    return changePassword(userRepository.getOne(userID), oldPassword, newPassword);
  }

  public User changePassword(User user, String oldPassword, String newPassword) {
    //validate old password
    String currentPassHash = user.getPassword();
    if (!passwordEncoder.matches(oldPassword, currentPassHash)) {
      throw new IllegalArgumentException("The password doesn't match");
    }
    if (passwordEncoder.matches(newPassword, currentPassHash)) {
      throw new IllegalArgumentException("The passwords are the same");
    }
    //set new password and save
    user.setPassword(passwordEncoder.encode(newPassword));
    return userRepository.save(user);
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
}
