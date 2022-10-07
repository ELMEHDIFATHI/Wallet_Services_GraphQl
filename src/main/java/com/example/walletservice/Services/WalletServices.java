package com.example.walletservice.Services;


import com.example.walletservice.Dto.addWalletDto;
import com.example.walletservice.Enums.TransactionType;
import com.example.walletservice.Models.Currency;
import com.example.walletservice.Models.Wallet;
import com.example.walletservice.Models.WalletTransaction;
import com.example.walletservice.Repositories.CurrencyRepo;
import com.example.walletservice.Repositories.WalletRepo;
import com.example.walletservice.Repositories.WalletTrasansactionRepo;

import org.springframework.core.io.ClassPathResource;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

import static java.lang.Double.parseDouble;

@Service
@Transactional
public class WalletServices{

    private CurrencyRepo currencyRepo;

    private WalletRepo walletRepo;

    private WalletTrasansactionRepo walletTrasansactionRepo;

    public WalletServices(CurrencyRepo currencyRepo, WalletRepo walletRepo, WalletTrasansactionRepo walletTrasansactionRepo) {
        this.currencyRepo = currencyRepo;
        this.walletRepo = walletRepo;
        this.walletTrasansactionRepo = walletTrasansactionRepo;
    }

    public List<WalletTransaction> walletTransfere(String sourceWalletId,String destinationWalletId,Double amount){
        Wallet sourceWallet=walletRepo.
                findById(sourceWalletId).orElseThrow(()->new RuntimeException(String.format("WALLET"+sourceWalletId+"Not Found")));
        Wallet DestinationWallet=walletRepo.
                findById(destinationWalletId).orElseThrow(()->new RuntimeException(String.format("WALLET"+destinationWalletId+"Not Found")));


       WalletTransaction swalletTransaction=WalletTransaction.builder()
               .timeStamp(System.currentTimeMillis())
               .transactionType(TransactionType.DEBIT)
               .amount(amount)
               .currentPurchaseCurrencyPrice(sourceWallet.getCurrency().getSalePrice())
               .currentPurchaseCurrencyPrice(sourceWallet.getCurrency().getPurchasePrice())
               .wallet(sourceWallet)
               .build();

       walletTrasansactionRepo.save(swalletTransaction);

       sourceWallet.setBalance(sourceWallet.getBalance()-amount);
        Double destinationAmount=amount*(DestinationWallet.getCurrency().getSalePrice()/sourceWallet.getCurrency().getPurchasePrice());
        WalletTransaction dwalletTransaction=WalletTransaction.builder()
                .timeStamp(System.currentTimeMillis())
                .transactionType(TransactionType.CREDIT)
                .amount(destinationAmount)
                .currentPurchaseCurrencyPrice(sourceWallet.getCurrency().getSalePrice())
                .currentPurchaseCurrencyPrice(sourceWallet.getCurrency().getPurchasePrice())
                .wallet(DestinationWallet)
                .build();
        walletTrasansactionRepo.save(dwalletTransaction);
        DestinationWallet.setBalance(sourceWallet.getBalance()+destinationAmount);

        return Arrays.asList(swalletTransaction,dwalletTransaction);
    }

    public Wallet savew(addWalletDto addWalletDto){
            Currency currency=currencyRepo.findById(addWalletDto.currencyCode()).orElseThrow(()->new RuntimeException(String.format("save walle %s not found", addWalletDto.currencyCode())));
            Wallet wallet=Wallet.builder()
                    .balance(addWalletDto.balance())
                    .id(UUID.randomUUID().toString())
                    .createdAt(System.currentTimeMillis())
                    .userId("user1")
                    .currency(currency)
                    .build();

           return walletRepo.save(wallet);

    }




    public void loadData() throws IOException {
        URI uri = new ClassPathResource("currencies.data.csv").getURI();
        Path path = Paths.get(uri);
        Files.readAllLines(path);
        List<String> lines = Files.readAllLines(path);
        for (int i = 1; i < lines.size(); i++) {
            String[] line = lines.get(i).split(";");
           Currency currency = Currency.builder()

                   .name(line[0])
                   .symbol(line[1])
                   .code(line[2])
                   .salePrice(Math.random()*16.4)
                   //.salePrice(Double.parseDouble(line[3]))
                  .purchasePrice(Math.random()*11.4)


                    .build();
           currencyRepo.save(currency);
        }



        Stream.of("MAD","USD","EUR","CAD").forEach(currencyCode ->{
            Currency currency=  currencyRepo.findById(currencyCode)
                    .orElseThrow(()->new RuntimeException
                            (String.format("Currency %s not fount",currencyCode)));
            Wallet wallet=new Wallet();
            wallet.setBalance(100000.0);
            wallet.setCurrency(currency);
            wallet.setCreatedAt(System.currentTimeMillis());
            wallet.setUserId("user1");
            wallet.setId(UUID.randomUUID().toString());
            walletRepo.save(wallet);

            });
        walletRepo.findAll().forEach(wallet->{
            for (int i = 0; i <10 ; i++) {
                WalletTransaction debitwalletTransaction = WalletTransaction.builder()
                        .amount(Math.random() * 100)
                        .wallet(wallet)
                        .transactionType(TransactionType.DEBIT)
                        .build();
                walletTrasansactionRepo.save(debitwalletTransaction);
                wallet.setBalance(wallet.getBalance()-debitwalletTransaction.getAmount());
                WalletTransaction creditwalletTransaction = WalletTransaction.builder()
                        .amount(Math.random() * 100)
                        .wallet(wallet)
                        .timeStamp(System.currentTimeMillis())
                        .transactionType(TransactionType.CREDIT)
                        .build();
                walletTrasansactionRepo.save(creditwalletTransaction);
                wallet.setBalance(wallet.getBalance()+debitwalletTransaction.getAmount());


            }


        });




    }
}