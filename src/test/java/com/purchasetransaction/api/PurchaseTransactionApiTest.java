package com.purchasetransaction.api;

import com.purchasetransaction.dto.ConvertedPurchaseTransactionResponse;
import com.purchasetransaction.dto.PurchaseTransactionRequest;
import com.purchasetransaction.dto.PurchaseTransactionResponse;
import com.purchasetransaction.service.PurchaseTransactionService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import static constant.TestContants.COUNTRY_CURRENCY;
import static constant.TestContants.PURCHASE_ID;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static stub.ConvertedPurchaseTransactionResponseStub.buildConvertedPurchaseResponse;
import static stub.PurchaseTransactionRequestStub.buildPurchaseTransactionRequest;
import static stub.PurchaseTransactionResponseStub.buildPurchaseTransactionResponse;

@ExtendWith(MockitoExtension.class)
class PurchaseTransactionApiTest {

    @InjectMocks
    private PurchaseTransactionApi api;

    @Mock
    private PurchaseTransactionService purchaseTransactionService;

    @Test
    void createPurchaseTransaction() {
        final PurchaseTransactionRequest request =
                buildPurchaseTransactionRequest();
        when(purchaseTransactionService.savePurchaseTransaction(any()))
                .thenReturn(buildPurchaseTransactionResponse());

        final ResponseEntity<PurchaseTransactionResponse> transactionResponse =
                api.storePurchaseTransaction(request);

        verify(purchaseTransactionService).savePurchaseTransaction(request);
        final ResponseEntity<PurchaseTransactionResponse> expected =
                ResponseEntity.ok(buildPurchaseTransactionResponse());
        assertEquals(expected, transactionResponse);
    }

    @Test
    void retrievePurchaseTransaction() {
        when(purchaseTransactionService.retrievePurchaseTransaction(any(),
                any())).thenReturn(buildConvertedPurchaseResponse());

        final ResponseEntity<ConvertedPurchaseTransactionResponse> transactionResponse =
                api.retrievePurchaseTransaction(PURCHASE_ID, COUNTRY_CURRENCY);

        verify(purchaseTransactionService).retrievePurchaseTransaction(
                PURCHASE_ID, COUNTRY_CURRENCY);
        final ResponseEntity<ConvertedPurchaseTransactionResponse> expected =
                ResponseEntity.ok(buildConvertedPurchaseResponse());
        assertEquals(expected, transactionResponse);
    }
}
