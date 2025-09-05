package com.teya.bankingsystem;

import com.teya.bankingsystem.model.Account;
import com.teya.bankingsystem.repository.AccountRepository;
import jakarta.servlet.ServletException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class BankingSystemControllerTest {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    AccountRepository accountRepository;

    @Test
    void createAccount_success() throws Exception {
        var requestJson = """
                {
                  "currency": "GBP"
                }
                """;

        var requestBuilder = patch("/create-account")
                .content(requestJson)
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.currency").value("GBP"))
                .andExpect(jsonPath("$.balance").value("100.0"));
    }

    @Test
    void createAccount_badCurrency() {
        var requestJson = """
                {
                  "currency": "USD"
                }
                """;

        var requestBuilder = patch("/create-account")
                .content(requestJson)
                .contentType(MediaType.APPLICATION_JSON);

        assertThrows(ServletException.class,
                () -> mockMvc.perform(requestBuilder)
                        .andExpect(status().isInternalServerError())
        );
    }

    @Test
    void getAccount() throws Exception {
        var account = accountRepository.save(new Account().setBalance(100).setCurrency("EUR"));

        var expectedJson = """
                {
                    "id": "%s",
                    "currency": "EUR",
                    "balance": 100.0
                }
                """.formatted(account.getId());

        var requestBuilder = get("/get-account/%s".formatted(account.getId()));

        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().json(expectedJson));
    }

    @Test
    void incomingTransaction() throws Exception {
        var account = accountRepository.save(new Account().setBalance(100).setCurrency("EUR"));

        var requestJson = """
                {
                  "accountId": "%s",
                  "currency": "EUR",
                  "amount": "12.34"
                }
                """.formatted(account.getId());

        var requestBuilder = post("/incoming-transaction")
                .content(requestJson)
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.balance").value("112.34"));
    }

    @Test
    void outgoingTransaction() throws Exception {
        var account = accountRepository.save(new Account().setBalance(100).setCurrency("EUR"));

        var requestJson = """
                {
                  "accountId": "%s",
                  "currency": "EUR",
                  "amount": "12.34"
                }
                """.formatted(account.getId());

        var requestBuilder = post("/outgoing-transaction")
                .content(requestJson)
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.balance").value("87.66"));
    }
}