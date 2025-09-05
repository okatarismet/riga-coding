package com.teya.bankingsystem.dto;

import com.teya.bankingsystem.model.Currency;
import lombok.Data;

@Data
public class AccountRequest {
    private Currency currency;
}
