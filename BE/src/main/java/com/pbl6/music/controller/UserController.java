package com.pbl6.music.controller;

//import com.pbl6.music.dto.request.UserRequestDTO;


import com.pbl6.music.dto.request.UserRequestDTO;
import com.pbl6.music.dto.response.UserResponseDTO;
import com.pbl6.music.entity.UserEntity;
import com.pbl6.music.service.IUserService;
import com.pbl6.music.util.ErrorCode;
import com.pbl6.music.util.ResponseData;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
//@CrossOrigin(origins = "*")
@RequestMapping("/api/v1/users")
public class UserController {


   IUserService userService;

    @GetMapping
    public ResponseData<?> getAllUser(@RequestParam(required = false, defaultValue = "1") int page,
                                      @RequestParam(required = false, defaultValue = "10") int pageSize) {
        return ResponseData.builder()
                .status(HttpStatus.OK.value())
                .code(ErrorCode.GET_SUCCESSFUL.getCode())
                .message(ErrorCode.GET_SUCCESSFUL.getMessage())
                .data(userService.getAllUser(page, pageSize))
                .build();
    }

    @GetMapping("/{id}")
    public ResponseData<?> getUserById(@PathVariable Long id) {
        return ResponseData.builder()
                .status(HttpStatus.OK.value())
                .code(ErrorCode.GET_SUCCESSFUL.getCode())
                .message(ErrorCode.GET_SUCCESSFUL.getMessage())
                .data(userService.getUserById(id))
                .build();
    }

    @GetMapping("/search")
    public ResponseData<?> searchUserByName(@RequestParam String name) {
        UserResponseDTO users = userService.getUserByUsername(name); // Giả sử bạn đã có phương thức trong userService
        return ResponseData.builder()
                .status(HttpStatus.OK.value())
                .code(ErrorCode.GET_SUCCESSFUL.getCode())
                .message(ErrorCode.GET_SUCCESSFUL.getMessage())
                .data(users)
                .build();
    }



    @DeleteMapping("/{id}")
    public ResponseData<?> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseData.builder()
                .status(HttpStatus.OK.value())
                .code(ErrorCode.DELETE_SUCCESSFUL.getCode())
                .message(ErrorCode.DELETE_SUCCESSFUL.getMessage())
                .message("Delete user success")
                .build();
    }




}
