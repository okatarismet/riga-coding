package com.teya.bankingsystem.dto;

import com.teya.bankingsystem.model.Currency;
import lombok.Data;

import java.util.UUID;

@Data
public class AccountTransactionRequest {
    private UUID accountId;
    private Currency currency;
    private double amount;
}
