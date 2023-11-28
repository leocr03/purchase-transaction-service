package com.purchasetransaction.service;

import com.purchasetransaction.client.FiscalDataTreasuryClient;
import com.purchasetransaction.client.response.ExchangeRateResponse;
import com.purchasetransaction.client.response.ExchangeRateResponseData;
import com.purchasetransaction.config.FiscalDataTreasuryConfig;
import com.purchasetransaction.dto.CurrencyConversion;
import com.purchasetransaction.mapper.CurrencyConversionMapper;
import com.purchasetransaction.mapper.ExchangeRateMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;

@Slf4j
@Service
@RequiredArgsConstructor
public class CurrencyConverterService {

    private final FiscalDataTreasuryClient fiscalDataTreasuryClient;
    private final FiscalDataTreasuryConfig fiscalDataTreasuryConfig;

    public CurrencyConversion getConvertedCurrency(String countryCurrency,
                                                   LocalDate purchaseDate,
                                                   BigDecimal purchaseAmount) {
        log.info("Converting purchase transaction. countryCurrency={}," +
                        "purchaseDate={}, purchaseAmount={}", countryCurrency,
                purchaseDate, purchaseAmount);
        final ExchangeRateResponse exchangeRate = getExchangeRateResponse(
                countryCurrency, purchaseDate);
        final CurrencyConversion currencyConversion = convertPurchaseTransaction(
                purchaseDate, purchaseAmount, exchangeRate);

        log.info("Purchase transaction converted. countryCurrency={}," +
                        "purchaseDate={}, purchaseAmount={}", countryCurrency,
                purchaseDate, purchaseAmount);
        return currencyConversion;
    }

    private CurrencyConversion convertPurchaseTransaction(
            LocalDate purchaseDate, BigDecimal purchaseAmount,
            ExchangeRateResponse exchangeRate) {
        final BigDecimal convertedAmount = convertCurrency(purchaseAmount,
                ExchangeRateMapper.mapExchangeRateToValue(exchangeRate));

        final ExchangeRateResponseData exchangeRateResponseData =
                ExchangeRateMapper.mapExchangeRateToData(exchangeRate);

        return CurrencyConversionMapper.mapToCurrencyConversion(purchaseDate,
                purchaseAmount, exchangeRateResponseData, convertedAmount);
    }

    private ExchangeRateResponse getExchangeRateResponse(String countryCurrency,
                                                         LocalDate purchaseDate) {
        final LocalDate startDate = purchaseDate.minusMonths(
                fiscalDataTreasuryConfig.getRatesOfExchangesMaxPeriodInMonths());

        return fiscalDataTreasuryClient
                .getExchangeRate(countryCurrency, startDate, purchaseDate);
    }

    private BigDecimal convertCurrency(BigDecimal purchaseAmount,
                                       BigDecimal exchangeRate) {
        return purchaseAmount.multiply(exchangeRate).setScale(2,
                RoundingMode.HALF_EVEN);
    }
}
