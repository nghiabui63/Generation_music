package com.pbl6.music.mapper;

import com.pbl6.music.dto.response.PurchaseResponse;
import com.pbl6.music.entity.Wallet;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface PurchaseMapper {
    PurchaseMapper INSTANCE = Mappers.getMapper(PurchaseMapper.class);

            @Mapping(source = "balance", target = "newBalance")

    PurchaseResponse toPurchaseResponse(Wallet wallet);
}
