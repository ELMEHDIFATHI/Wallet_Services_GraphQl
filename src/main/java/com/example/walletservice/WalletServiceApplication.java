package com.example.walletservice;

import com.example.walletservice.Services.WalletServices;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class WalletServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(WalletServiceApplication.class, args);
    }

    @Bean
    CommandLineRunner start(WalletServices walletServices){
        return  a->{
            walletServices.loadData();
        };
    }

}
