package com.pbl6.music.service;

import com.pbl6.music.dto.request.LoginRequestDTO;
import com.pbl6.music.dto.request.UserRegisterRequest;
import com.pbl6.music.dto.response.AuthenticationResponse;
import com.pbl6.music.entity.UserEntity;
import com.pbl6.music.entity.Wallet;
import com.pbl6.music.mapper.UserMapper;
import com.pbl6.music.repository.UserRepository;
import com.pbl6.music.repository.WalletRepository;
import com.pbl6.music.util.AppException;
import com.pbl6.music.util.ErrorCode;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Collections;
import java.util.Date;

@Service
@Transactional
public class AuthenticationService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    @Autowired
    private WalletRepository walletRepository;

    @Autowired
    public AuthenticationService(
            UserRepository userRepository,
            UserMapper userMapper,
            PasswordEncoder passwordEncoder,
            JwtService jwtService,
            AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    public AuthenticationResponse register(UserRegisterRequest request) {
        // Kiểm tra nếu tên người dùng đã tồn tại
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new RuntimeException("Username already exists");
        }

        // Kiểm tra nếu email đã tồn tại
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        // Tạo người dùng mới
        UserEntity user = userMapper.toEntity(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        // Lưu người dùng vào cơ sở dữ liệu trước
        user = userRepository.save(user); // Lưu người dùng trước để có ID

        // Tạo ví mới với số dư mặc định là 0
        Wallet wallet = new Wallet();
        wallet.setBalance(BigDecimal.ZERO); // Số dư mặc định
        wallet.setUpdatedAt(Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant())); // Thiết lập thời gian hiện tại cho updated_at

        // Liên kết ví với người dùng
        wallet.setUser(user); // Thiết lập user cho wallet

        // Lưu ví vào cơ sở dữ liệu
        walletRepository.save(wallet); // Lưu ví mới tạo

        String token = jwtService.generateToken(loadUserDetails(user));

        return AuthenticationResponse.builder()
                .token(token)
                .user(userMapper.toDTO(user))
                .build();
    }


    public AuthenticationResponse login(LoginRequestDTO request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getUsername(),
                            request.getPassword()
                    )
            );
        } catch (AuthenticationException e) {
            throw new BadCredentialsException("Invalid username or password");
        }

        UserEntity user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        String token = jwtService.generateToken(loadUserDetails(user));

        return AuthenticationResponse.builder()
                .token(token)
                .user(userMapper.toDTO(user))
                .build();
    }

    private UserDetails loadUserDetails(UserEntity user) {
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                Collections.singleton(new SimpleGrantedAuthority(user.getRole()))
        );
    }
}