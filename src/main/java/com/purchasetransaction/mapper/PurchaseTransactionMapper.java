package com.purchasetransaction.mapper;

import com.purchasetransaction.dto.PurchaseTransactionRequest;
import com.purchasetransaction.dto.PurchaseTransactionResponse;
import com.purchasetransaction.entity.PurchaseTransaction;

public class PurchaseTransactionMapper {

    public static PurchaseTransaction mapRequestToEntity(
            PurchaseTransactionRequest request) {
        return new PurchaseTransaction(request.getDescription(),
                request.getTransactionDate(),
                request.getOriginalPurchaseAmount());
    }

    public static PurchaseTransactionResponse mapEntityToResponse(
            PurchaseTransaction entity) {
        return PurchaseTransactionResponse.builder()
                .purchaseId(entity.getPurchaseId())
                .description(entity.getDescription())
                .transactionDate(entity.getTransactionDate())
                .originalPurchaseAmount(entity.getOriginalPurchaseAmount())
                .build();
    }
}
