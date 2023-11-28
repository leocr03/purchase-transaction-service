package com.purchasetransaction.service;

import com.purchasetransaction.dto.ConvertedPurchaseTransactionResponse;
import com.purchasetransaction.dto.CurrencyConversion;
import com.purchasetransaction.dto.PurchaseTransactionRequest;
import com.purchasetransaction.dto.PurchaseTransactionResponse;
import com.purchasetransaction.entity.PurchaseTransaction;
import com.purchasetransaction.mapper.ConvertedPurchaseTransactionMapper;
import com.purchasetransaction.mapper.PurchaseTransactionMapper;
import com.purchasetransaction.repository.PurchaseTransactionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

import static com.purchasetransaction.constant.MessageConstants.PURCHASE_ID_NOT_FOUND_MESSAGE;

@Slf4j
@Service
@RequiredArgsConstructor
public class PurchaseTransactionService {

    private final PurchaseTransactionRepository purchaseTransactionRepository;
    private final CurrencyConverterService currencyConverterService;

    public PurchaseTransactionResponse savePurchaseTransaction(
            PurchaseTransactionRequest request) {
        log.info("Saving purchase transaction. request={}", request);
        final PurchaseTransaction purchaseTransaction =
                PurchaseTransactionMapper.mapRequestToEntity(request);

        final PurchaseTransaction savedPurchaseTransaction =
                purchaseTransactionRepository.save(purchaseTransaction);

        final PurchaseTransactionResponse response =
                PurchaseTransactionMapper.mapEntityToResponse(
                        savedPurchaseTransaction);
        log.info("Purchase transaction saved. response={}", response);
        return response;
    }

    public ConvertedPurchaseTransactionResponse retrievePurchaseTransaction(
            UUID purchaseId, String countryCurrency) {
        log.info("Retrieving purchase transaction. purchaseId={}," +
                "countryCurrency={}", purchaseId, countryCurrency);
        final PurchaseTransaction purchaseTransaction = getPurchaseTransaction(
                purchaseId);

        final CurrencyConversion convertedCurrency = currencyConverterService
                .getConvertedCurrency(countryCurrency,
                        purchaseTransaction.getTransactionDate(),
                        purchaseTransaction.getOriginalPurchaseAmount());

        final ConvertedPurchaseTransactionResponse response =
                ConvertedPurchaseTransactionMapper.mapCurrencyConversionToResponse(
                        purchaseTransaction, convertedCurrency);
        log.info("Purchase transaction retrieved. response={}", response);
        return response;
    }

    private PurchaseTransaction getPurchaseTransaction(UUID purchaseId) {
        final Optional<PurchaseTransaction> purchaseOpt =
                purchaseTransactionRepository.findByPurchaseId(purchaseId);

        if (purchaseOpt.isEmpty()) {
            log.warn("No purchase transaction found. message={}, " +
                    "purchaseId={}", PURCHASE_ID_NOT_FOUND_MESSAGE,
                    purchaseId);
            throw new EmptyResultDataAccessException(
                    PURCHASE_ID_NOT_FOUND_MESSAGE, 1);
        }

        return purchaseOpt.get();
    }
}
