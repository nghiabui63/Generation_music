package com.pbl6.music.dto.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserResponseDTO implements Serializable {
     private Long id;
     private String username;
     private String email;
     private String fullName;
     private String phoneNumber;
//     private String address;
     private String role;
     Long WalletId;
}
