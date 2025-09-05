package com.teya.bankingsystem.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Currency {

    @Id
    private String code;
}
