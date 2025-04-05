package com.quocdoansam.walletx.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.quocdoansam.walletx.dto.request.UserCreationRequest;
import com.quocdoansam.walletx.dto.response.ApiResponse;
import com.quocdoansam.walletx.dto.response.UserResponse;
import com.quocdoansam.walletx.exception.AppException;
import com.quocdoansam.walletx.exception.ErrorCode;
import com.quocdoansam.walletx.service.UserService;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@Slf4j
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/users")
    ApiResponse<UserResponse> create(@RequestBody @Valid UserCreationRequest request) {
        UserResponse userResponse = userService.create(request);
        return ApiResponse.<UserResponse>builder().result(userResponse).build();
    }

    @GetMapping("/{username}")
    public ApiResponse<UserResponse> read(@PathVariable String username) {

        String currentUsername = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        if (!currentUsername.equals(username)) {
            throw new AppException(ErrorCode.FORBIDDEN);
        }

        UserResponse userResponse = userService.read(username);
        return ApiResponse.<UserResponse>builder().result(userResponse).build();
    }

    @GetMapping("/users")
    public ApiResponse<List<UserResponse>> readAll() {
        List<UserResponse> users = userService.readAll();
        return ApiResponse.<List<UserResponse>>builder().result(users).build();
    }
}
