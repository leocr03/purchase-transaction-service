package com.purchasetransaction.mapper;

import com.purchasetransaction.dto.ConvertedPurchaseTransactionResponse;
import com.purchasetransaction.dto.CurrencyConversion;
import com.purchasetransaction.entity.PurchaseTransaction;

public class ConvertedPurchaseTransactionMapper {

    public static ConvertedPurchaseTransactionResponse mapCurrencyConversionToResponse(
            PurchaseTransaction entity, CurrencyConversion currencyConversion) {
        return ConvertedPurchaseTransactionResponse.builder()
                .purchaseId(entity.getPurchaseId())
                .description(entity.getDescription())
                .transactionDate(entity.getTransactionDate())
                .originalPurchaseAmount(entity.getOriginalPurchaseAmount())
                .exchangeRateUsed(currencyConversion.getExchangeRate())
                .convertedPurchaseAmount(
                        currencyConversion.getConvertedPurchaseAmount())
                .build();
    }
}
