package com.pbl6.music.controller;

import com.pbl6.music.dto.request.LoginRequestDTO;
import com.pbl6.music.dto.request.UserRegisterRequest;
import com.pbl6.music.dto.response.AuthenticationResponse;
import com.pbl6.music.service.AuthenticationService;
import com.pbl6.music.util.ResponseData;
import com.pbl6.music.util.SuccessCode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<ResponseData<?>> register(@RequestBody UserRegisterRequest request) {
        return ResponseEntity.ok(
                ResponseData.builder()
                        .code(SuccessCode.CREATED.getCode())
                        .message(SuccessCode.CREATED.getMessage())
                        .data(authenticationService.register(request ))
                        .build()
        );
    }

    @PostMapping("/login")
    public ResponseEntity<ResponseData<?>> login(@RequestBody LoginRequestDTO request) {
        return ResponseEntity.ok(
                ResponseData.builder()
                        .code(SuccessCode.LOGIN.getCode())
                        .message(SuccessCode.LOGIN.getMessage())
                        .data(authenticationService.login(request))
                        .build()
        );
    }
}
