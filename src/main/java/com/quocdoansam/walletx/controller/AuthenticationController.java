package com.quocdoansam.walletx.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nimbusds.jose.JOSEException;
import com.quocdoansam.walletx.dto.request.AuthenticationRequest;
import com.quocdoansam.walletx.dto.request.IntrospectRequest;
import com.quocdoansam.walletx.dto.response.AuthResponse;
import com.quocdoansam.walletx.dto.response.AuthenticationResponse;
import com.quocdoansam.walletx.dto.response.BaseResponse;
import com.quocdoansam.walletx.dto.response.IntrospectResponse;
import com.quocdoansam.walletx.service.AuthenticationService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.text.ParseException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationController {

    AuthenticationService authenticationService;

    @PostMapping("/token")
    public BaseResponse<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request,
            HttpServletResponse response) {
        var result = authenticationService.authenticate(request);

        Cookie cookie = new Cookie("access_token", result.getToken());
        cookie.setHttpOnly(true); // Important: Not allowed JavaScript to read
        cookie.setSecure(true); // Only send cookie through HTTPS (Enable while production)
        cookie.setPath("/");
        cookie.setMaxAge(60 * 60 * 24 * 30); // 30 days
        cookie.setAttribute("SameSite", "None"); // Allowed website send cookie cross-origin
        response.addCookie(cookie);

        response.addCookie(cookie);

        return BaseResponse.<AuthenticationResponse>builder()
                .success(true)
                .statusCode(HttpStatus.OK.value())
                .message("Logged in successful.")
                .data(result)
                .build();
    }

    @PostMapping("/introspect")
    public BaseResponse<IntrospectResponse> introspect(@RequestBody IntrospectRequest request)
            throws JOSEException, ParseException {
        var result = authenticationService.introspect(request);

        return BaseResponse.<IntrospectResponse>builder()
                .success(true)
                .statusCode(HttpStatus.OK.value())
                .message("Verified token successful.")
                .data(result)
                .build();
    }

    // @GetMapping("/auth/check")
    // public ResponseEntity<?> checkAuth(Authentication authentication) {
    // if (authentication == null || !authentication.isAuthenticated()) {
    // return
    // ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("authenticated",
    // false));
    // }
    // return ResponseEntity.ok(Map.of("authenticated", true));
    // }

    @GetMapping() // Authorization
    public ResponseEntity<BaseResponse<AuthResponse>> checkAuth(Authentication authentication) {
        boolean isAuthenticated = authentication != null && authentication.isAuthenticated()
                && !(authentication instanceof AnonymousAuthenticationToken);
        AuthResponse response = new AuthResponse(isAuthenticated);

        if (!isAuthenticated) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                    BaseResponse.<AuthResponse>builder()
                            .success(false)
                            .statusCode(HttpStatus.UNAUTHORIZED.value())
                            .message("User is not authenticated.")
                            .data(response)
                            .build());
        }

        return ResponseEntity.ok(
                BaseResponse.<AuthResponse>builder()
                        .success(true)
                        .statusCode(HttpStatus.OK.value())
                        .message("User is authenticated.")
                        .data(response)
                        .build());
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletResponse response) {
        // Init cookie
        Cookie cookie = new Cookie("access_token", null);
        cookie.setHttpOnly(false);
        cookie.setSecure(false);
        cookie.setPath("/");
        cookie.setMaxAge(0);

        // Remove cookie
        response.addCookie(cookie);

        // Clear context
        SecurityContextHolder.clearContext();

        return ResponseEntity.ok(
                BaseResponse.builder()
                        .success(true)
                        .statusCode(HttpStatus.OK.value())
                        .message("Logged out successful.")
                        .build());
    }

}
