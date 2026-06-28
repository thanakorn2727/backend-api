package com.thanakornget.backendjavaapi.controllers;

import com.thanakornget.backendjavaapi.model.request.UsersRequest;
import com.thanakornget.backendjavaapi.model.response.APIResponse;
import com.thanakornget.backendjavaapi.model.response.UsersResponse;
import com.thanakornget.backendjavaapi.service.UsersService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
public class UsersController {
    private final UsersService usersService;

    public UsersController(UsersService usersService) {
        this.usersService = usersService;
    }

    @PostMapping("/users")
    public ResponseEntity<APIResponse<UsersResponse>> createUser(
            @Valid @RequestBody UsersRequest request) {
        UsersResponse user = usersService.createUser(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(APIResponse.success("Create user success", user));
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<APIResponse<UsersResponse>> getUserById(@PathVariable Long id) {
        UsersResponse user = usersService.getUserById(id);
        return ResponseEntity.ok(APIResponse.success("Get user success", user));
    }

    @GetMapping("/users")
    public ResponseEntity<APIResponse<List<UsersResponse>>> getUsers() {
        List<UsersResponse> users = usersService.getUsers();
        return ResponseEntity.ok(APIResponse.success("Get users success", users));
    }

    @GetMapping("/users/search")
    public ResponseEntity<APIResponse<List<UsersResponse>>> searchUsers(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String status) {
        List<UsersResponse> users = usersService.searchUsers(keyword, status);
        return ResponseEntity.ok(APIResponse.success("Search users success", users));
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<APIResponse<UsersResponse>> updateUser(
            @PathVariable Long id,
            @Valid @RequestBody UsersRequest request) {
        UsersResponse user = usersService.updateUser(id, request);
        return ResponseEntity.ok(APIResponse.success("Update user success", user));
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<APIResponse<Map<String, Object>>> deleteUser(@PathVariable Long id) {
        usersService.deleteUser(id);
        return ResponseEntity.ok(
                APIResponse.success("Delete user success", Map.of())
        );
    }
}
