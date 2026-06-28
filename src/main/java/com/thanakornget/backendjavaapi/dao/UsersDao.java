package com.thanakornget.backendjavaapi.dao;

import com.thanakornget.backendjavaapi.model.response.UsersResponse;

import java.util.List;

public interface UsersDao {
    List<UsersResponse> searchUsers(String keyword, String status);
}
