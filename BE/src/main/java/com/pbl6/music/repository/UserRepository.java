package com.pbl6.music.repository;

import com.pbl6.music.dto.response.UserResponseDTO;
import com.pbl6.music.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
@Query("SELECT new com.pbl6.music.dto.response.UserResponseDTO(u.id, u.username, u.email, u.fullName, u.role, u.phoneNumber, w.walletId) " +
           "FROM UserEntity u LEFT JOIN Wallet w ON u.id = w.user.id")
    Page<UserResponseDTO> findAllUser(Pageable pageable);

    Optional<UserEntity> findByUsername(String username);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
   @Query("SELECT new com.pbl6.music.dto.response.UserResponseDTO(u.id, u.username, u.email, u.fullName, u.role, u.phoneNumber, w.walletId) " +
           "FROM UserEntity u LEFT JOIN Wallet w ON u.id = w.user.id WHERE u.username = :username")
    Optional<UserResponseDTO> findUserByUsername(String username);
}