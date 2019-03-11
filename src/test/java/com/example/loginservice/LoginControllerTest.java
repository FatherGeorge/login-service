package com.example.loginservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.junit.Assert.assertThat;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class LoginControllerTest {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void loginReturnsNotFoundIfProfessorNotInDb() throws Exception {
        // Setup
        final Login request = new Login("James", "123");

        // Execute
        final String actualStringResponse = mockMvc.perform(post("/login")
                                                   .contentType(MediaType.APPLICATION_JSON)
                                                   .content(OBJECT_MAPPER.writeValueAsString(request)))
                                                   .andExpect(status().isOk())
                                                   .andReturn()
                                                   .getResponse()
                                                   .getContentAsString();
        LoginResponse actualResponse = OBJECT_MAPPER.readValue(actualStringResponse, LoginResponse.class);

        // Assert
        assertThat(actualResponse, allOf(hasProperty("statusCode", is("-1")),
                                         hasProperty("statusDescription", is("Account not found"))
        ));
    }

    @Test
    public void loginReturnsLoginSuccessfulProfessorInDb() throws Exception {
        // Setup
        final Login request = new Login("Euler", "2.72");

        // Execute
        final String actualStringResponse = mockMvc.perform(post("/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(OBJECT_MAPPER.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        LoginResponse actualResponse = OBJECT_MAPPER.readValue(actualStringResponse, LoginResponse.class);

        // Assert
        assertThat(actualResponse, allOf(hasProperty("statusCode", is("1")),
                hasProperty("statusDescription", is("Login successful"))
        ));
    }
}
