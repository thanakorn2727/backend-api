package com.thanakornget.backendjavaapi.service;

import com.thanakornget.backendjavaapi.dao.UsersDao;
import com.thanakornget.backendjavaapi.entity.UsersEntity;
import com.thanakornget.backendjavaapi.exception.ResourceNotFoundException;
import com.thanakornget.backendjavaapi.model.request.UsersRequest;
import com.thanakornget.backendjavaapi.model.response.UsersResponse;
import com.thanakornget.backendjavaapi.repository.UsersRepository;
import com.thanakornget.backendjavaapi.service.impl.UsersServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UsersServiceImplTest {

    @Mock
    private UsersRepository usersRepository;

    @Mock
    private UsersDao usersDao;

    private UsersServiceImpl usersService;

    @BeforeEach
    void setUp() {
        usersService = new UsersServiceImpl(usersRepository, usersDao);
    }

    @Test
    void createUserShouldConvertBirthDateAndSaveUser() {
        UsersRequest request = validRequest();
        when(usersRepository.countByUsername("Thanakorn")).thenReturn(0L);
        when(usersRepository.save(any(UsersEntity.class))).thenAnswer(invocation -> {
            UsersEntity entity = invocation.getArgument(0);
            entity.setId(1L);
            return entity;
        });

        UsersResponse response = usersService.createUser(request);

        ArgumentCaptor<UsersEntity> captor = ArgumentCaptor.forClass(UsersEntity.class);
        verify(usersRepository).save(captor.capture());
        UsersEntity savedUser = captor.getValue();

        assertThat(response.getId()).isEqualTo(1L);
        assertThat(savedUser.getIdCard()).isEqualTo("1234567890098");
        assertThat(savedUser.getBirthDate())
                .isEqualTo(LocalDateTime.of(1998, 10, 27, 0, 0));
    }

    @Test
    void createUserShouldRejectInvalidBirthDate() {
        UsersRequest request = validRequest();
        request.setBirthDate("31/02/1998");

        assertThatThrownBy(() -> usersService.createUser(request))
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining("400 BAD_REQUEST");

        verify(usersRepository, never()).save(any());
    }

    @Test
    void getUserByIdShouldReturnUserWhenFound() {
        when(usersRepository.findUserById(2L)).thenReturn(Optional.of(userEntity(2L)));

        UsersResponse response = usersService.getUserById(2L);

        assertThat(response.getId()).isEqualTo(2L);
        assertThat(response.getFirstName()).isEqualTo("Thanakorn");
    }

    @Test
    void getUserByIdShouldThrowWhenUserDoesNotExist() {
        when(usersRepository.findUserById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> usersService.getUserById(99L))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("User not found");
    }

    @Test
    void getUsersShouldReturnMappedUsers() {
        when(usersRepository.findAllUsers()).thenReturn(List.of(userEntity(1L), userEntity(2L)));

        List<UsersResponse> users = usersService.getUsers();

        assertThat(users).hasSize(2);
        assertThat(users).extracting(UsersResponse::getId).containsExactly(1L, 2L);
    }

    @Test
    void deleteUserShouldThrowWhenNothingWasUpdated() {
        when(usersRepository.deleteUserById(99L)).thenReturn(0);

        assertThatThrownBy(() -> usersService.deleteUser(99L))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("User not found");
    }

    private UsersRequest validRequest() {
        UsersRequest request = new UsersRequest();
        request.setIdCard("1234567890098");
        request.setFirstName("Thanakorn");
        request.setLastName("Wannasing");
        request.setBirthDate("27/10/1998");
        request.setAddress("12/4 Sathorn Bangkok");
        request.setSubDistrictId(102801L);
        request.setDistrictId(28L);
        request.setProvinceId(1L);
        request.setPostalCode(10120L);
        request.setEmail("thanakorn@gmail.com");
        request.setPhoneNumber("0611112300");
        return request;
    }

    private UsersEntity userEntity(Long id) {
        UsersEntity entity = new UsersEntity();
        entity.setId(id);
        entity.setIdCard("1234567890098");
        entity.setFirstName("Thanakorn");
        entity.setLastName("Wannasing");
        entity.setBirthDate(LocalDateTime.of(1998, 10, 27, 0, 0));
        entity.setAddress("12/4 Sathorn Bangkok");
        entity.setEmail("thanakorn@gmail.com");
        entity.setPhoneNumber("0611112300");
        entity.setActive("Y");
        return entity;
    }
}
