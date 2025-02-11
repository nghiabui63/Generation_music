package com.pbl6.music.controller;

import com.pbl6.music.dto.request.PurchaseRequest;
import com.pbl6.music.dto.response.PurchaseResponse;
import com.pbl6.music.entity.Wallet;
import com.pbl6.music.service.WalletService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/api/wallet")
public class WalletController {
    WalletService walletService;

    @GetMapping("/{id}")
    public ResponseEntity<Wallet> getWalletById(@PathVariable Long id) {
        Optional<Wallet> wallet = walletService.getWalletById(id);
        return wallet.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Cập nhật số dư ví
    @PutMapping("/{id}")
    public ResponseEntity<Wallet> updateWalletBalance(@PathVariable Long id, @RequestBody BigDecimal amount) {
        Wallet updatedWallet = walletService.updateWalletBalance(id, amount);
        return ResponseEntity.ok(updatedWallet);
    }
    @PostMapping("/purchaseMusic")
    public ResponseEntity<PurchaseResponse> purchaseMusic(@RequestBody PurchaseRequest request) {
        try {
            PurchaseResponse response = walletService.purchaseMusic(request);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(new PurchaseResponse(null));
        }
    }
}
