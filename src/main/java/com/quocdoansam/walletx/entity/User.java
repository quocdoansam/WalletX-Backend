package com.quocdoansam.walletx.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data // Auto create methods getter, setter, toString, equals, hashCode
@NoArgsConstructor // Create a constructor no arguments
@AllArgsConstructor // Create a constructor arguments for all fields
@Builder // Allowed Builder pattern to init object
@FieldDefaults(level = AccessLevel.PRIVATE) // Set default all fields is private if not defined
@Entity // define this class is an entity, mapping JPA 
public class User {
    
    @Id
    @GeneratedValue(strategy = GenerationType.UUID) // Auto generate Id
    String id;

    @Column(unique = true, nullable = false)
    String username;

    @Column(nullable = false)
    String password;

    @Column(unique = true, nullable = true)
    String email;

    String fullName;
    LocalDate dob;

    @Builder.Default
    @Column(unique = true)
    String walletAddress = UUID.randomUUID().toString();

    @Builder.Default
    @Column(columnDefinition = "DECIMAL(19, 4)")
    BigDecimal balance = BigDecimal.ZERO;

    Set<String> roles;

    @CreationTimestamp
    LocalDate createdAt;
}

