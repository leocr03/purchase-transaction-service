package com.purchasetransaction.mapper;

import com.purchasetransaction.dto.ConvertedPurchaseTransactionResponse;
import com.purchasetransaction.dto.CurrencyConversion;
import com.purchasetransaction.entity.PurchaseTransaction;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static stub.ConvertedPurchaseTransactionResponseStub.buildConvertedPurchaseResponse;
import static stub.CurrencyConversionStub.buildCurrencyConversion;
import static stub.PurchaseTransactionEntityStub.buildPurchaseTransactionEntity;

class ConvertedPurchaseTransactionMapperTest {

    @Test
    void mapCurrencyConversionToResponse() {
        final PurchaseTransaction entity = buildPurchaseTransactionEntity();
        final CurrencyConversion currencyConversion = buildCurrencyConversion();

        final ConvertedPurchaseTransactionResponse response =
                ConvertedPurchaseTransactionMapper
                .mapCurrencyConversionToResponse(entity, currencyConversion);

        assertEquals(buildConvertedPurchaseResponse(), response);
    }
}
