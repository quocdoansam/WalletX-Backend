package com.quocdoansam.walletx.dto.response;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

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
public class UserResponse {
    String id;
    String username;
    String email;
    String fullName;
    LocalDate dob;
    String walletAddress;
    BigDecimal balance;
    Set<String> roles;
    LocalDate createdAt;
}
