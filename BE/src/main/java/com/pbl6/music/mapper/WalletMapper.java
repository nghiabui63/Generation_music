package com.pbl6.music.mapper;

import com.pbl6.music.dto.request.UserRegisterRequest;
import com.pbl6.music.dto.response.UserResponseDTO;
import com.pbl6.music.dto.response.WalletResponseDTO;
import com.pbl6.music.entity.UserEntity;
import com.pbl6.music.entity.Wallet;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface WalletMapper {

    WalletResponseDTO toDTO(Wallet Wallet);


    Wallet toEntity(WalletResponseDTO walletResponseDTO);

}
