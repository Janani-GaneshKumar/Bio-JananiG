package com.janani.contentrecommendation.controller;
import com.janani.contentrecommendation.dto.PreferenceRequest;
import com.janani.contentrecommendation.dto.UserResponse;
import com.janani.contentrecommendation.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // GET /users/{id} → Get user profile by ID
    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable Long id) {
        UserResponse response = userService.getUserById(id);
        return ResponseEntity.ok(response);
    }

    // PUT /users/{id}/preferences → Update categories/tags
    @PutMapping("/{id}/preferences")
    public ResponseEntity<UserResponse> updatePreferences(
            @PathVariable Long id,
            @Valid @RequestBody PreferenceRequest request) {
        UserResponse response = userService.updatePreferences(id, request);
        return ResponseEntity.ok(response);
    }

    // PUT /users/{id}/become-curator → User decides to opt-in as curator
    @PutMapping("/{id}/become-curator")
    public ResponseEntity<UserResponse> becomeCurator(@PathVariable Long id) {
        UserResponse response = userService.becomeCurator(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/profile")
    public ResponseEntity<String> getProfile()
    { return ResponseEntity.ok("This is a protected profile endpoint!"); }
}
