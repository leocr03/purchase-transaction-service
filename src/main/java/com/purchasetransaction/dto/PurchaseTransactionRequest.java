package com.purchasetransaction.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

import static com.purchasetransaction.constant.MessageConstants.AMOUNT_CANNOT_BE_BLANK_MESSAGE;
import static com.purchasetransaction.constant.MessageConstants.DESCRIPTION_CANNOT_BE_BLANK_MESSAGE;
import static com.purchasetransaction.constant.MessageConstants.INVALID_DESCRIPTION_MESSAGE;
import static com.purchasetransaction.constant.MessageConstants.INVALID_PURCHASE_AMOUNT_MESSAGE;
import static com.purchasetransaction.constant.MessageConstants.PURCHASE_AMOUNT_MESSAGE_IS_OUT_OF_LIMIT;
import static com.purchasetransaction.constant.MessageConstants.TRANSACTION_DATE_CANNOT_BE_BLANK_MESSAGE;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PurchaseTransactionRequest {

    @Schema(description = "The description of the purchase transaction.",
            example = "Purchase of diesel.")
    @NotBlank(message = DESCRIPTION_CANNOT_BE_BLANK_MESSAGE)
    @Size(min = 1, max = 50, message = INVALID_DESCRIPTION_MESSAGE)
    private String description;

    @Schema(description = "The date of the purchase transaction.",
            example = "2023-11-29")
    @NotNull(message = TRANSACTION_DATE_CANNOT_BE_BLANK_MESSAGE)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate transactionDate;

    @Schema(description = "The amount value in USA Dollars of the purchase transaction.",
            example = "10.0")
    @NotNull(message = AMOUNT_CANNOT_BE_BLANK_MESSAGE)
    @DecimalMin(value = "0.00", inclusive = false,
            message = INVALID_PURCHASE_AMOUNT_MESSAGE)
    @Digits(integer = 15, fraction = 2,
            message = PURCHASE_AMOUNT_MESSAGE_IS_OUT_OF_LIMIT)
    private BigDecimal originalPurchaseAmount;
}
