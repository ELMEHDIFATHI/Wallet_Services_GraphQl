package com.example.walletservice.Repositories;

import com.example.walletservice.Models.WalletTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WalletTrasansactionRepo extends JpaRepository<WalletTransaction,Long> {
}
