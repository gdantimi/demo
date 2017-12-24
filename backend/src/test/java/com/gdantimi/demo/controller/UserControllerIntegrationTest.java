package com.gdantimi.demo.controller;

import com.gdantimi.demo.AbstractRestIntegrationTest;
import com.gdantimi.demo.controller.error.RestError;
import com.gdantimi.demo.model.dto.UserDto;
import com.gdantimi.demo.repository.UserRepository;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

import static com.gdantimi.demo.DatabaseSetup.DBUNIT_USERS;
import static com.github.springtestdbunit.annotation.DatabaseOperation.DELETE_ALL;
import static java.lang.Boolean.TRUE;
import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.beans.HasPropertyWithValue.hasProperty;
import static org.hamcrest.collection.IsIterableContainingInOrder.contains;
import static org.springframework.http.HttpStatus.*;


public class UserControllerIntegrationTest extends AbstractRestIntegrationTest {

    @LocalServerPort
    int port;

    @Autowired
    private UserRepository userRepository;

    TestRestTemplate restTemplate = new TestRestTemplate();
    private String userUrl;

    @Before
    public void setup() {
        userUrl = "http://localhost:" + port + "/users/";
    }

    @Test
    public void getOperation_shouldReturnNotFoundWhenUserIsNotInDatabase() {
        UriComponentsBuilder builder = UriComponentsBuilder
                .fromUriString(userUrl)
                .path("1");
        ResponseEntity<UserDto> response = restTemplate.getForEntity(builder.toUriString(), UserDto.class);
        assertThat(response.getStatusCode(), equalTo(NOT_FOUND));
        assertThat(response.getBody(), nullValue());
    }

    @Test
    @DatabaseSetup(value = DBUNIT_USERS)
    public void getOperation_shouldReturnUserWhenUserIsInDatabase() {
        UriComponentsBuilder builder = UriComponentsBuilder
                .fromUriString(userUrl)
                .path("1");
        ResponseEntity<UserDto> response = restTemplate.getForEntity(builder.toUriString(), UserDto.class);
        assertThat(response.getStatusCode(), equalTo(OK));
        assertThat(response.getBody(), allOf(
                hasProperty("id", equalTo(1L)),
                hasProperty("name", equalTo("Name")),
                hasProperty("sectorsIds", contains(1L, 2L)),
                hasProperty("termsAgreed", equalTo(TRUE))
        ));
    }

    @Test
    @DatabaseSetup(value = DBUNIT_USERS, type = DELETE_ALL)
    public void postOperation_shouldSaveUserWhenUserIsNotInDatabase() {
        UriComponentsBuilder builder = UriComponentsBuilder
                .fromUriString(userUrl);
        String name = "Name";
        UserDto userDto = UserDto.builder()
                .name(name)
                .sectorsIds(asList(1L, 2L))
                .termsAgreed(TRUE)
                .build();
        ResponseEntity<UserDto> response = restTemplate.postForEntity(builder.toUriString(), userDto, UserDto.class);

        assertThat(userRepository.count(), equalTo(1L));
        assertThat(response.getStatusCode(), equalTo(OK));
        assertThat(response.getBody(), allOf(
                hasProperty("id", notNullValue()),
                hasProperty("name", equalTo(name)),
                hasProperty("sectorsIds", contains(1L, 2L)),
                hasProperty("termsAgreed", equalTo(TRUE))
        ));
    }

    @Test
    @DatabaseSetup(value = DBUNIT_USERS)
    public void postOperation_shouldReturnConflictWhenUserWithSameNameIsAlreadyInDatabase() {
        UriComponentsBuilder builder = UriComponentsBuilder
                .fromUriString(userUrl);
        String name = "Name";
        UserDto userDto = UserDto.builder()
                .name(name)
                .sectorsIds(asList(1L, 2L))
                .termsAgreed(TRUE)
                .build();
        ResponseEntity<RestError> response = restTemplate.postForEntity(builder.toUriString(), userDto, RestError.class);

        assertThat(response.getStatusCode(), equalTo(CONFLICT));
        assertThat(response.getBody(),
                hasProperty("errors", contains(
                        hasProperty("field", equalTo("name"))
                        )
                ));
    }

    @Test
    @DatabaseSetup(value = DBUNIT_USERS)
    public void putOperation_shouldReturnConflictWhenUserWithSameNameIsAlreadyInDatabase() {
        UriComponentsBuilder builder = UriComponentsBuilder
                .fromUriString(userUrl);
        long userId = 1L;
        String name = "Name2";
        UserDto userDto = UserDto.builder()
                .id(userId)
                .name(name)
                .sectorsIds(asList(1L, 2L))
                .termsAgreed(TRUE)
                .build();
        RequestEntity<UserDto> request = RequestEntity
                .put(URI.create(builder.toUriString()))
                .body(userDto);
        ResponseEntity<UserDto> response = restTemplate.exchange(request, UserDto.class);

        assertThat(response.getStatusCode(), equalTo(OK));
        assertThat(response.getBody(), allOf(
                        hasProperty("id", equalTo(userId)),
                        hasProperty("name", equalTo(name)),
                        hasProperty("sectorsIds", contains(1L, 2L)),
                        hasProperty("termsAgreed", equalTo(TRUE))
                ));
    }

}
