package com.purchasetransaction.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@AllArgsConstructor
@Document
@Data
public class PurchaseTransaction {

    @Id
    private String transactionId;
    private UUID purchaseId;
    private String description;
    private LocalDate transactionDate;
    private BigDecimal originalPurchaseAmount;

    public PurchaseTransaction() {
        purchaseId = UUID.randomUUID();
    }

    public PurchaseTransaction(String description, LocalDate transactionDate,
                               BigDecimal originalPurchaseAmount) {
        this();
        transactionId = null;
        this.description = description;
        this.transactionDate = transactionDate;
        this.originalPurchaseAmount = originalPurchaseAmount;
    }
}
