package com.purchasetransaction.service;

import com.purchasetransaction.dto.ConvertedPurchaseTransactionResponse;
import com.purchasetransaction.dto.PurchaseTransactionRequest;
import com.purchasetransaction.dto.PurchaseTransactionResponse;
import com.purchasetransaction.entity.PurchaseTransaction;
import com.purchasetransaction.repository.PurchaseTransactionRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.EmptyResultDataAccessException;
import stub.CurrencyConversionStub;

import java.util.Optional;

import static constant.TestContants.COUNTRY_CURRENCY;
import static constant.TestContants.ORIGINAL_PURCHASE_AMOUNT;
import static constant.TestContants.PURCHASE_DATE;
import static constant.TestContants.PURCHASE_ID;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static stub.ConvertedPurchaseTransactionResponseStub.buildConvertedPurchaseResponse;
import static stub.PurchaseTransactionEntityStub.buildPurchaseTransactionEntity;
import static stub.PurchaseTransactionRequestStub.buildPurchaseTransactionRequest;
import static stub.PurchaseTransactionResponseStub.buildPurchaseTransactionResponse;

@ExtendWith(MockitoExtension.class)
class TransactionServiceTest {

    @InjectMocks
    private PurchaseTransactionService service;
    @Mock
    private PurchaseTransactionRepository repository;
    @Mock
    private CurrencyConverterService currencyConverterService;

    @Test
    void savePurchaseTransaction() {
        final PurchaseTransactionRequest request =
                buildPurchaseTransactionRequest();
        final PurchaseTransaction transaction =
                buildPurchaseTransactionEntity();
        when(repository.save(any())).thenReturn(transaction);

        final PurchaseTransactionResponse response =
                service.savePurchaseTransaction(request);

        assertPurchaseIsSaved(response);
    }

    @Test
    void retrievePurchaseTransaction() {
        when(repository.findByPurchaseId(any())).thenReturn(Optional.of(
                buildPurchaseTransactionEntity()));
        when(currencyConverterService.getConvertedCurrency(any(), any(), any()))
                .thenReturn(CurrencyConversionStub.buildCurrencyConversion());

        final ConvertedPurchaseTransactionResponse response = service
                .retrievePurchaseTransaction(PURCHASE_ID, COUNTRY_CURRENCY);

        verify(repository).findByPurchaseId(PURCHASE_ID);
        verify(repository).findByPurchaseId(PURCHASE_ID);
        verify(currencyConverterService).getConvertedCurrency(COUNTRY_CURRENCY,
                PURCHASE_DATE, ORIGINAL_PURCHASE_AMOUNT);
        assertEquals(buildConvertedPurchaseResponse(), response);
    }

    @Test
    void retrievePurchaseTransaction_whenNotFound_thenThrowException() {
        when(repository.findByPurchaseId(any())).thenReturn(Optional.empty());

        try {
            service.retrievePurchaseTransaction(PURCHASE_ID, COUNTRY_CURRENCY);
            fail();
        } catch (EmptyResultDataAccessException ignored) {}
    }

    private void assertPurchaseIsSaved(PurchaseTransactionResponse response) {
        assertPurchaseId();
        assertOtherFields(response);
    }

    private void assertPurchaseId() {
        final ArgumentCaptor<PurchaseTransaction> transactionCaptor =
                ArgumentCaptor.forClass(PurchaseTransaction.class);
        verify(repository).save(transactionCaptor.capture());

        final PurchaseTransaction entitySaved = transactionCaptor.getValue();
        assertNotNull(entitySaved.getPurchaseId());
    }

    private static void assertOtherFields(PurchaseTransactionResponse response) {
        final PurchaseTransactionResponse expected =
                buildPurchaseTransactionResponse();
        assertAll(() -> {
            assertNotNull(response.getPurchaseId());
            assertEquals(expected.getDescription(), response.getDescription());
            assertEquals(expected.getTransactionDate(),
                    response.getTransactionDate());
            assertEquals(expected.getOriginalPurchaseAmount(),
                    response.getOriginalPurchaseAmount());
        });
    }
}
