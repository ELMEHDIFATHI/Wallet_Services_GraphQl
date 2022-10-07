package com.example.walletservice.Web;

import com.example.walletservice.Dto.addWalletDto;
import com.example.walletservice.Models.Currency;
import com.example.walletservice.Models.Wallet;
import com.example.walletservice.Models.WalletTransaction;
import com.example.walletservice.Repositories.CurrencyRepo;
import com.example.walletservice.Repositories.WalletRepo;
import com.example.walletservice.Services.WalletServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class WalletGraphql {

    private WalletRepo walletRepo;
    private WalletServices walletServices;
     private CurrencyRepo currencyRepo;

    public WalletGraphql(WalletRepo walletRepo, WalletServices walletServices, CurrencyRepo currencyRepo) {
        this.walletRepo = walletRepo;
        this.walletServices = walletServices;
        this.currencyRepo = currencyRepo;
    }

    @QueryMapping
    public List<Wallet> userWallets(){
            return walletRepo.findAll();
    }

    @QueryMapping
    public Wallet walletById(@Argument String id){
        return walletRepo.findById(id).orElseThrow( ()->
                new RuntimeException(String.format("wallet id % not fount",id))
        );

    }
    @MutationMapping
    public List<WalletTransaction> walletTransfere(@Argument  String sourceWalletId,@Argument String desWalletId,@Argument Double amount){
        return walletServices.walletTransfere(sourceWalletId, desWalletId, amount);

    }


    @MutationMapping
    public Wallet addWallet(@Argument addWalletDto walletDto){
        return walletServices.savew(walletDto);


    }

    @QueryMapping
    public List<Currency> currencies(){
            return currencyRepo.findAll();
    }
}


