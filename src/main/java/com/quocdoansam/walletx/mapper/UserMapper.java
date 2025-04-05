package com.quocdoansam.walletx.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;

import com.quocdoansam.walletx.dto.request.UserCreationRequest;
import com.quocdoansam.walletx.dto.response.UserResponse;
import com.quocdoansam.walletx.entity.User;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface UserMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "dob", ignore = true)
    @Mapping(target = "walletAddress", ignore = true)
    @Mapping(target = "balance", ignore = true)
    @Mapping(target = "email", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    User toCreationUser(UserCreationRequest request);

    UserResponse toUserResponse(User user);
}
