package bg.codeacademy.spring.gossiptalks;

import jdk.nashorn.internal.ir.annotations.Ignore;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;


@SpringBootTest
  @AutoConfigureMockMvc
  public class LoginTest {
    @Autowired
    private MockMvc mvc;

    @Test
    public void Login_with_user_should_succeed() throws Exception {
      mvc.perform(MockMvcRequestBuilders.post("/login")
          .param("username", "user")
          .param("password", "user"))
          .andDo(MockMvcResultHandlers.print());
    }
  }

