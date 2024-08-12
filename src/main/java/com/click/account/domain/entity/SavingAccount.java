package com.click.account.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "SAVING_ACCOUNTS")
public class SavingAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "CREATE_AT")
    private LocalDate createAt;

    @Column(name = "END_AT")
    private LocalDate endAT;

    @Column(name = "INTEREST_RATE")
    private Double interestRate;

    @Column(name = "PRODUCT_NAME")
    private String product;

    @Column(name = "MY_ACCOUNT")
    private String myAccount;

    @Column(name = "SEND_ACCOUNT")
    private String sendAccount;
}
