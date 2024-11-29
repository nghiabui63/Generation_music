package com.pbl6.music.repository;

import com.pbl6.music.entity.TransactionHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<TransactionHistory, Long> {
    TransactionHistory findByTransactionRef(String vnpTxnRef);
}