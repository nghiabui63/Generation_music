package com.pbl6.music.service;

import com.pbl6.music.dto.request.UserRequestDTO;
import com.pbl6.music.dto.response.UserResponseDTO;
import com.pbl6.music.dto.response.PageResponse;

import java.util.UUID;


public interface IUserService {

    PageResponse<?> getAllUser(int page, int pageSize);
    UserResponseDTO getUserById(Long id);
    UserResponseDTO getUserByUsername(String username);

    void deleteUser(Long id);

}
