package com.teya.bankingsystem.repository;

import com.teya.bankingsystem.model.Currency;
import org.springframework.data.repository.CrudRepository;

public interface CurrencyRepository extends CrudRepository<Currency, String> {

}
