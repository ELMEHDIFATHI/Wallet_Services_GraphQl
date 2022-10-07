package com.example.walletservice.Models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class Currency {
    @Id
    private String code;
    private String name;

    private String symbol;
    private double salePrice;
    private double purchasePrice;

}