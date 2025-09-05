package com.teya.bankingsystem.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.experimental.Accessors;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Data
@Entity
@Accessors(chain = true)
public class Account {

    @Id
    @UuidGenerator
    private UUID id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "currency_code")
    private Currency currency;

    @Column(nullable = false)
    private double balance;
}
