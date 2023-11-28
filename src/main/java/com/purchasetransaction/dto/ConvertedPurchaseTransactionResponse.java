package com.purchasetransaction.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ConvertedPurchaseTransactionResponse {

    @Schema(description = "The unique identifier of the purchase transaction.",
            example = "550e8400-e29b-41d4-a716-446655440000")
    private UUID purchaseId;

    @Schema(description = "The description of the purchase transaction.",
            example = "Purchase of diesel.")
    private String description;

    @Schema(description = "The date of the purchase transaction.",
            example = "2023-11-29")
    private LocalDate transactionDate;

    @Schema(description = "The exchange rate used to convert the purchase amount to the currency value of the specified country.",
            example = "5.033")
    private BigDecimal exchangeRateUsed;

    @Schema(description = "The amount value in USA Dollars of the purchase transaction.",
            example = "10.0")
    private BigDecimal originalPurchaseAmount;

    @Schema(description = "The amount value converted from USA Dollars to the currency value of the specified country.",
            example = "50.33")
    private BigDecimal convertedPurchaseAmount;
}
