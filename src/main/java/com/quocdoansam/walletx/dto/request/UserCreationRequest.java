package com.quocdoansam.walletx.dto.request;

import java.util.Set;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserCreationRequest {

    @Size(min = 3, max = 30, message = "USERNAME_INVALID")
    @Pattern(regexp = "^[A-Za-z][A-Za-z0-9_]*$", message = "USERNAME_MUST_START_WITH_LETTER")
    String username;

    @Size(min = 6, max = 60, message = "PASSWORD_INVALID")
    String password;
    String fullName;
    Set<String> roles;
}
