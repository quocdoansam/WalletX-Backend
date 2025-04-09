package com.quocdoansam.walletx.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.quocdoansam.walletx.dto.request.UserCreationRequest;
import com.quocdoansam.walletx.dto.response.ApiResponse;
import com.quocdoansam.walletx.dto.response.BaseResponse;
import com.quocdoansam.walletx.dto.response.UserResponse;
import com.quocdoansam.walletx.enums.ErrorMessage;
import com.quocdoansam.walletx.exception.BaseException;
import com.quocdoansam.walletx.service.UserService;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@Slf4j
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/users")
    ResponseEntity<BaseResponse<UserResponse>> create(@RequestBody @Valid UserCreationRequest request) {
        UserResponse userResponse = userService.create(request);
        return ResponseEntity.ok(
                BaseResponse.<UserResponse>builder()
                        .success(true)
                        .statusCode(HttpStatus.OK.value())
                        .message("The account has been created.")
                        .data(userResponse)
                        .build());
    }

    @GetMapping("/{username}")
    public ResponseEntity<BaseResponse<UserResponse>> read(@PathVariable String username) {

        String currentUsername = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        UserResponse userResponse = userService.read(username);

        if (!currentUsername.equals(username)) {
            throw new BaseException(ErrorMessage.ACCESS_DENIED);
        }

        return ResponseEntity.ok(
                BaseResponse.<UserResponse>builder()
                        .success(true)
                        .statusCode(HttpStatus.OK.value())
                        .message("Get user successful.")
                        .data(userResponse)
                        .build());
    }

    @GetMapping("/users")
    public ResponseEntity<BaseResponse<List<UserResponse>>> readAll() {
        List<UserResponse> users = userService.readAll();
        return ResponseEntity.ok(
                BaseResponse.<List<UserResponse>>builder()
                        .success(true)
                        .statusCode(HttpStatus.OK.value())
                        .message("Get users successful.")
                        .data(users)
                        .build());
    }

    @GetMapping()
    public ResponseEntity<BaseResponse<UserResponse>> me() {
        UserResponse userResponse = userService.me();
        return ResponseEntity.ok(
                BaseResponse.<UserResponse>builder()
                        .success(true)
                        .statusCode(HttpStatus.OK.value())
                        .message("Fetch me successful.")
                        .data(userResponse)
                        .build());
    }

}
