package com.example.walletservice.Repositories;

import com.example.walletservice.Models.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WalletRepo extends JpaRepository<Wallet,String> {
}
