package com.biletimcepte.controller;

import com.biletimcepte.dto.request.RegisterRequest;
import com.biletimcepte.model.enums.GenderType;
import com.biletimcepte.model.enums.UserType;
import com.biletimcepte.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
class UserControllerTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private UserService userService;

    @Test
    @WithMockUser(username = "ADMIN", password = "ADMIN", authorities = "ADMIN")
    public void registerUserAPI() throws Exception
    {
        mvc.perform(MockMvcRequestBuilders
                        .post("/users")
                        .content(asJsonString(new RegisterRequest("elifUser",
                                "123elif123",
                                "elif@yahoo.com",
                                "Elif",
                                "Yavuzaslan",
                                23,
                                GenderType.FEMALE,
                                UserType.CORPORATE,
                                "05554443322")))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    public static String asJsonString(final Object object) {
        try {
            return new ObjectMapper().writeValueAsString(object);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
