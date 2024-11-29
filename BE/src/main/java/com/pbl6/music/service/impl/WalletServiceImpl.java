package com.pbl6.music.service.impl;

import com.pbl6.music.dto.request.PurchaseRequest;
import com.pbl6.music.dto.response.PurchaseResponse;
import com.pbl6.music.entity.*;
import com.pbl6.music.mapper.PurchaseMapper;
import com.pbl6.music.repository.MusicRepository;
import com.pbl6.music.repository.PurchaseRepository;
import com.pbl6.music.repository.UserRepository;
import com.pbl6.music.repository.WalletRepository;
import com.pbl6.music.service.WalletService;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class WalletServiceImpl implements WalletService {
    WalletRepository walletRepository;
    UserRepository userRepository;
    MusicRepository musicRepository;
    PurchaseRepository purchaseRepository;
    @Override
    public Optional<Wallet> getWalletById(Long id) {
        return walletRepository.findById(id);
    }
    public Wallet updateWalletBalance(Long id, BigDecimal amount) {
        Wallet wallet = walletRepository.findById(id).orElseThrow(() -> new RuntimeException("Wallet not found"));
        wallet.setBalance(amount);
        return walletRepository.save(wallet);
    }

    @Override
    @Transactional
    public PurchaseResponse purchaseMusic(PurchaseRequest request) {
        UserEntity buyer = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        UserEntity composer = userRepository.findById(request.getComposerId())
                .orElseThrow(() -> new RuntimeException("Composer not found"));

        // Kiểm tra số dư ví của người mua
        if (buyer.getWallet().getBalance().compareTo(request.getPrice()) < 0) {
            throw new RuntimeException("Insufficient funds");
        }

        // Tính toán số tiền giao dịch
        BigDecimal composerAmount = request.getPrice().multiply(BigDecimal.valueOf(0.5));
        BigDecimal buyerAmount = request.getPrice();

        // Cập nhật số dư ví
        buyer.getWallet().setBalance(buyer.getWallet().getBalance().subtract(buyerAmount));
        composer.getWallet().setBalance(composer.getWallet().getBalance().add(composerAmount));

        userRepository.save(buyer);
        userRepository.save(composer);

        // Lưu thông tin giao dịch vào bảng Purchase
        Music music = musicRepository.findById(request.getMusicId())
                .orElseThrow(() -> new RuntimeException("Music not found")); // Tìm nhạc theo ID

        Purchase purchase = new Purchase();
        purchase.setUser(buyer);  // Sử dụng đối tượng UserEntity
        purchase.setMusic(music);
        purchase.setPurchaseDate(new Date()); // Lưu ngày giờ hiện tại
        purchase.setAmount(request.getPrice()); // Lưu giá trị mua

        purchaseRepository.save(purchase); // Lưu vào bảng purchase

        // Cập nhật trạng thái đã mua của nhạc
        music.setPurchased(true); // Giả sử `isPurchased` là kiểu boolean hoặc có thể là Integer, hãy chắc chắn rằng nó phù hợp
        musicRepository.save(music); // Lưu cập nhật nhạc

        return PurchaseMapper.INSTANCE.toPurchaseResponse(buyer.getWallet());
    }

    @Override
    public void updateBalance(Long walletId, BigDecimal amount, BalanceUpdateType type) {
        Wallet wallet = walletRepository.findById(walletId)
                .orElseThrow(() -> new RuntimeException("Wallet not found"));

        BigDecimal newBalance;
        switch (type) {
            case DEPOSIT:
                newBalance = wallet.getBalance().add(amount);
                break;
            case WITHDRAW:
                newBalance = wallet.getBalance().subtract(amount);
                if (newBalance.compareTo(BigDecimal.ZERO) < 0) {
                    throw new RuntimeException("Insufficient balance");
                }
                break;
            default:
                throw new RuntimeException("Invalid update type");
        }

        wallet.setBalance(newBalance);
        walletRepository.save(wallet);
    }

}
