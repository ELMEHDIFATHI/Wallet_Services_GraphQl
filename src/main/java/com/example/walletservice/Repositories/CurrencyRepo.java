package com.example.walletservice.Repositories;

import com.example.walletservice.Models.Currency;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CurrencyRepo extends JpaRepository<Currency,String> {



}
