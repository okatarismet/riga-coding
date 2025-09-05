package com.teya.bankingsystem.controller;

import com.teya.bankingsystem.service.BankgingSystemService;
import com.teya.bankingsystem.dto.AccountRequest;
import com.teya.bankingsystem.dto.AccountTransactionRequest;
import com.teya.bankingsystem.model.Account;
import com.teya.bankingsystem.model.Currency;
import com.teya.bankingsystem.repository.AccountRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
import com.teya.bankingsystem.service.BankingSystemService;
@Slf4j
@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class BankingSystemController {

    private AccountRepository accountRepository;
    private BankingSystemService bankingSystemService;

    public BankingSystemController(AccountRepository accountRepository, BankingSystemService bankingSystemService){
        this.accountRepository = accountRepository;
        this.bankingSystemService = bankingSystemService;
    }

    @GetMapping("get-account/{account_id}")
    public Account getAccount(@PathVariable("account_id") UUID accountId) {
        return accountRepository.findById(accountId).orElseThrow();
    }

    @PatchMapping("create-account")
    public ResponseEntity<?> createAccount(@RequestBody AccountRequest request) {
        validateCurrency(request.getCurrency());

        var account = new Account()
                .setBalance(100)
                .setCurrency(request.getCurrency());

        accountRepository.save(account);
        log.info("New account created: {}", account);

        return ResponseEntity.status(HttpStatus.OK).body(account);
    }

    @PostMapping("incoming-transaction")
    public ResponseEntity<IncomingTransactionDto> incomingTransaction(@RequestBody AccountTransactionRequest request) {

        IncomingTransactionDto result = bankingSystemService.incomingTransaction(request);
        return ResponseEntity.ok(result);
    }

    @PostMapping("outgoing-transaction")
    public ResponseEntity<?> outgoingTransaction(@RequestBody AccountTransactionRequest request) {
        validateCurrency(request.getCurrency());

        var account = accountRepository.findById(request.getAccountId()).orElseThrow();

        account.setBalance(account.getBalance() - request.getAmount());

        account = accountRepository.save(account);
        log.info("Account updated: {}", account);

        return ResponseEntity.ok(account);
    }

    private void validateCurrency(Currency currency) {
        if (!currency.getCode().equalsIgnoreCase("EUR") && !currency.getCode().equalsIgnoreCase("GBP")) {
            throw new RuntimeException("Illegal currency");
        }
    }
}
