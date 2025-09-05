package com.teya.bankingsystem.service;

import com.teya.bankingsystem.dto.AccountTransactionRequest;
import com.teya.bankingsystem.dto.IncomingTransactionDto;
import com.teya.bankingsystem.model.Currency;
import com.teya.bankingsystem.repository.AccountRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public class BankingSystemService {

    private final AccountRepository accountRepository;
    private final Logger log = LoggerFactory.getLogger(BankingSystemService.class);

    public BankingSystemService(AccountRepository accountRepository){
        this.accountRepository = accountRepository;
    }
    public IncomingTransactionDto incomingTransaction(@RequestBody AccountTransactionRequest request){
        validateCurrency(request.getCurrency());

        var account = accountRepository.findById(request.getAccountId()).orElseThrow();

        account.setBalance(account.getBalance() + request.getAmount());

        accountRepository.save(account);
        return new IncomingTransactionDto("true",1);
    }
    private void validateCurrency(Currency currency) {
        if (!currency.getCode().equalsIgnoreCase("EUR") && !currency.getCode().equalsIgnoreCase("GBP")) {
            throw new RuntimeException("Illegal currency");
        }
    }
}
