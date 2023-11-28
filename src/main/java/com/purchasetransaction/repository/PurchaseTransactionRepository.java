package com.purchasetransaction.repository;

import com.purchasetransaction.entity.PurchaseTransaction;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.UUID;

public interface PurchaseTransactionRepository
        extends CrudRepository<PurchaseTransaction, String> {
    Optional<PurchaseTransaction> findByPurchaseId(UUID purchaseId);
}
