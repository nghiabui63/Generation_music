package com.pbl6.music.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaymentRequest {
    private BigDecimal amount;
    private String orderInfo;
    private String ipAddress;
    private String walletId;

    private String transactionRef;

    // Getters and setters
}
