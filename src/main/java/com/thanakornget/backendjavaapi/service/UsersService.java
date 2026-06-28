package com.thanakornget.backendjavaapi.service;

import com.thanakornget.backendjavaapi.model.request.UsersRequest;
import com.thanakornget.backendjavaapi.model.response.UsersResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public interface UsersService {
   UsersResponse createUser(UsersRequest request);
   UsersResponse getUserById(Long id);
   List<UsersResponse> getUsers();
    List<UsersResponse> searchUsers(String keyword, String status);
    UsersResponse updateUser(Long id, UsersRequest request);
    void deleteUser(Long id);


}
