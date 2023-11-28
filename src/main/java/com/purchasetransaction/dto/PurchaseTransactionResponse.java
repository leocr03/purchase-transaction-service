package com.purchasetransaction.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import static com.purchasetransaction.constant.MessageConstants.INVALID_PURCHASE_AMOUNT_MESSAGE;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PurchaseTransactionResponse {

    @Schema(description = "The unique identifier of the purchase transaction. It's in UUID format.",
            example = "550e8400-e29b-41d4-a716-446655440000")
    private UUID purchaseId;

    @Schema(description = "The description of the purchase transaction.",
            example = "Purchase of diesel.")
    private String description;

    @Schema(description = "The date of the purchase transaction.",
            example = "2023-11-29")
    private LocalDate transactionDate;

    @Schema(description = "The amount value in USA Dollars of the purchase transaction.",
            example = INVALID_PURCHASE_AMOUNT_MESSAGE)
    private BigDecimal originalPurchaseAmount;
}
