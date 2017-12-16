package com.gdantimi.demo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gdantimi.demo.model.dto.UserDto;
import com.gdantimi.demo.service.UserService;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import static java.lang.Boolean.TRUE;
import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static org.hamcrest.Matchers.*;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@EnableWebMvc
@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
public class UserControllerUnitTest {
    public static final int MAX_NAME_SIZE = 255;
    @Autowired
    private UserController userController;

    @MockBean
    private UserService userService;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void shouldReturnBadRequestResponseWhenMandatoryDataIsMissing() throws Exception {
        UserDto user = UserDto.builder()
                .build();

        mockMvc.perform(post("/users/")
                .contentType(APPLICATION_JSON_UTF8)
                .content(mapper.writeValueAsString(user)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("errors", iterableWithSize(3)))
                .andExpect(jsonPath("errors[*].field")
                        .value(containsInAnyOrder("name", "sectorsIds", "termsAgreed")))
                .andExpect(jsonPath("errors[*].rejectedValue")
                        .value(containsInAnyOrder(is(nullValue()), is(nullValue()), is(false))));
    }

    @Test
    public void shouldReturnBadRequestResponseWhenNameIsTooShort() throws Exception {
        String name = "aa";
        UserDto user = UserDto.builder()
                .name(name)
                .sectorsIds(singletonList(1L))
                .termsAgreed(TRUE)
                .build();

        mockMvc.perform(post("/users/")
                .contentType(APPLICATION_JSON_UTF8)
                .content(mapper.writeValueAsString(user)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("errors", iterableWithSize(1)))
                .andExpect(jsonPath("errors[0].field", is("name")))
                .andExpect(jsonPath("errors[0].rejectedValue", is(name)));
    }

    @Test
    public void shouldReturnBadRequestResponseWhenNameIsTooLong() throws Exception {
        String name = RandomStringUtils.randomAlphabetic(MAX_NAME_SIZE + 1);
        UserDto user = UserDto.builder()
                .name(name)
                .sectorsIds(singletonList(1L))
                .termsAgreed(TRUE)
                .build();

        mockMvc.perform(post("/users/")
                .contentType(APPLICATION_JSON_UTF8)
                .content(mapper.writeValueAsString(user)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("errors", iterableWithSize(1)))
                .andExpect(jsonPath("errors[0].field", is("name")))
                .andExpect(jsonPath("errors[0].rejectedValue", is(name)));
    }

    @Test
    public void shouldReturnBadRequestResponseWhenSectorsIdsIsEmpty() throws Exception {
        UserDto user = UserDto.builder()
                .name("name")
                .sectorsIds(emptyList())
                .termsAgreed(TRUE)
                .build();

        mockMvc.perform(post("/users/")
                .contentType(APPLICATION_JSON_UTF8)
                .content(mapper.writeValueAsString(user)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("errors", iterableWithSize(1)))
                .andExpect(jsonPath("errors[0].field", is("sectorsIds")))
                .andExpect(jsonPath("errors[0].rejectedValue", is(emptyList())));
    }

    @Test
    public void shouldReturnOkResponseWhenMandatoryDataIsValid() throws Exception {
        String name = "name";
        UserDto user = UserDto.builder()
                .name(name)
                .sectorsIds(singletonList(1L))
                .termsAgreed(TRUE)
                .build();

        UserDto responseUser = UserDto.builder()
                .id(1L)
                .name(name)
                .build();

        when(userService.save(eq(user))).thenReturn(responseUser);

        mockMvc.perform(post("/users/")
                .contentType(APPLICATION_JSON_UTF8)
                .content(mapper.writeValueAsString(user)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("name", is(name)))
                .andExpect(jsonPath("id", is(notNullValue())));
    }
}