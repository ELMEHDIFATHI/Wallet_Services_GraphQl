package com.example.walletservice.Models;

import com.example.walletservice.Enums.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WalletTransaction {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private  long timeStamp;
    private Double amount;
    private  Double currentSaleCurrencyPrice;
    private  Double currentPurchaseCurrencyPrice;
    @ManyToOne
    private Wallet wallet;
    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;
}
