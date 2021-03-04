package bg.codeacademy.spring.gossiptalks.config;

import org.springframework.boot.autoconfigure.security.reactive.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, jsr250Enabled = true, securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http. csrf()
        /**/.disable()
        .cors()
        /**/.disable()
        .headers()
        /**/.frameOptions().sameOrigin().and()
        .formLogin()
        /**/.and()
        .httpBasic()
        /**/.and()
        .logout()
        /**/.and()
        .authorizeRequests()
        /**/.antMatchers("/h2-console/**").permitAll()
        /**/.antMatchers(HttpMethod.POST,"/api/v1/users").permitAll()
        /**/.antMatchers("/**").permitAll();//????
    // /**/.antMatchers("/**").authenticated();

    //requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll();


  }

  @Bean
  PasswordEncoder getPasswordEncoder() {
    return new BCryptPasswordEncoder();
  }

}
