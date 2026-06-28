package com.thanakornget.backendjavaapi.controllers;

import com.thanakornget.backendjavaapi.exception.GlobalExceptionHandler;
import com.thanakornget.backendjavaapi.exception.ResourceNotFoundException;
import com.thanakornget.backendjavaapi.model.request.UsersRequest;
import com.thanakornget.backendjavaapi.model.response.UsersResponse;
import com.thanakornget.backendjavaapi.service.UsersService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class UsersControllerTest {

    @Mock
    private UsersService usersService;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders
                .standaloneSetup(new UsersController(usersService))
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void getUserByIdShouldReturnWrappedUser() throws Exception {
        when(usersService.getUserById(2L)).thenReturn(userResponse(2L));

        mockMvc.perform(get("/api/v1/users/2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Get user success"))
                .andExpect(jsonPath("$.data.id").value(2))
                .andExpect(jsonPath("$.data.firstName").value("Thanakorn"));
    }

    @Test
    void getUserByIdShouldReturnEmptyObjectWhenNotFound() throws Exception {
        when(usersService.getUserById(99L))
                .thenThrow(new ResourceNotFoundException("User not found"));

        mockMvc.perform(get("/api/v1/users/99"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.message").value("User not found"))
                .andExpect(jsonPath("$.data").isMap())
                .andExpect(jsonPath("$.data").isEmpty());
    }

    @Test
    void getUsersShouldReturnEmptyArray() throws Exception {
        when(usersService.getUsers()).thenReturn(List.of());

        mockMvc.perform(get("/api/v1/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data").isEmpty());
    }

    @Test
    void createUserShouldReturnCreatedUser() throws Exception {
        UsersRequest request = validRequest();
        when(usersService.createUser(any(UsersRequest.class))).thenReturn(userResponse(1L));

        mockMvc.perform(post("/api/v1/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Create user success"))
                .andExpect(jsonPath("$.data.id").value(1));

        verify(usersService).createUser(any(UsersRequest.class));
    }

    private UsersRequest validRequest() {
        UsersRequest request = new UsersRequest();
        request.setIdCard("1234567890098");
        request.setFirstName("Thanakorn");
        request.setLastName("Wannasing");
        request.setBirthDate("27/10/1998");
        request.setAddress("12/4 Sathorn Bangkok");
        request.setEmail("thanakorn@gmail.com");
        request.setPhoneNumber("0611112300");
        return request;
    }

    private UsersResponse userResponse(Long id) {
        return new UsersResponse(
                id,
                "1234567890098",
                "Thanakorn",
                "Wannasing",
                LocalDateTime.of(1998, 10, 27, 0, 0),
                "12/4 Sathorn Bangkok",
                102801L,
                28L,
                1L,
                10120L,
                "thanakorn@gmail.com",
                "0611112300",
                null,
                null,
                null,
                null,
                "Y"
        );
    }
}
