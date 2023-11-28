package com.purchasetransaction.mapper;

import com.purchasetransaction.dto.PurchaseTransactionRequest;
import com.purchasetransaction.dto.PurchaseTransactionResponse;
import com.purchasetransaction.entity.PurchaseTransaction;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static stub.PurchaseTransactionEntityStub.buildNotPersistedPurchaseEntity;
import static stub.PurchaseTransactionEntityStub.buildPurchaseTransactionEntity;
import static stub.PurchaseTransactionRequestStub.buildPurchaseTransactionRequest;
import static stub.PurchaseTransactionResponseStub.buildPurchaseTransactionResponse;

class PurchaseTransactionMapperTest {

    @Test
    void mapRequestToEntity() {
        final PurchaseTransactionRequest request =
                buildPurchaseTransactionRequest();

        final PurchaseTransaction purchaseTransaction =
                PurchaseTransactionMapper.mapRequestToEntity(request);

        assertMapToEntity(purchaseTransaction);
    }

    @Test
    void mapEntityToResponse() {
        final PurchaseTransaction entity = buildPurchaseTransactionEntity();

        final PurchaseTransactionResponse response =
                PurchaseTransactionMapper.mapEntityToResponse(entity);

        final PurchaseTransactionResponse expected =
                buildPurchaseTransactionResponse();
        assertEquals(expected, response);
    }

    private static void assertMapToEntity(PurchaseTransaction purchaseTransaction) {
        assertAll(() -> {
            assertNull(purchaseTransaction.getTransactionId());
            assertNotNull(purchaseTransaction.getPurchaseId());
            final PurchaseTransaction expected = buildNotPersistedPurchaseEntity();
            assertEquals(expected.getDescription(),
                    purchaseTransaction.getDescription());
            assertEquals(expected.getTransactionDate(),
                    purchaseTransaction.getTransactionDate());
            assertEquals(expected.getOriginalPurchaseAmount(),
                    purchaseTransaction.getOriginalPurchaseAmount());
        });
    }
}