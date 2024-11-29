package com.pbl6.music.mapper;

import com.pbl6.music.dto.request.UserRegisterRequest;
import com.pbl6.music.dto.request.UserRequestDTO;
import com.pbl6.music.dto.response.UserResponseDTO;
import com.pbl6.music.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {
   @Mapping(source = "wallet.walletId", target = "WalletId")
    UserResponseDTO toDTO(UserEntity user);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "role", constant = "USER")
    @Mapping(target = "purchases", ignore = true)

    UserEntity toEntity(UserRegisterRequest request);

    List<UserResponseDTO> toDTOList(List<UserEntity> users);
}
