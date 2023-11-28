package com.purchasetransaction.mapper;

import com.purchasetransaction.client.response.ExchangeRateResponse;
import com.purchasetransaction.client.response.ExchangeRateResponseData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;

import java.math.BigDecimal;
import java.util.Optional;

import static com.purchasetransaction.constant.MessageConstants.PURCHASE_CANNOT_BE_CONVERTED_MESSAGE;

@Slf4j
public class ExchangeRateMapper {

    public static BigDecimal mapExchangeRateToValue(
            ExchangeRateResponse exchangeRate) {
        return Optional.ofNullable(
                exchangeRate.getExchangeRateResponseDataList())
                .filter(list -> !list.isEmpty())
                .map(list -> list.get(0))
                .map(ExchangeRateResponseData::getExchangeRate)
                .orElseThrow(() -> {
                    log.warn("No exchange rate found. exchangeRate={}",
                            exchangeRate);
                    return new EmptyResultDataAccessException(
                            PURCHASE_CANNOT_BE_CONVERTED_MESSAGE, 1);
                });
    }

    public static ExchangeRateResponseData mapExchangeRateToData(
            ExchangeRateResponse exchangeRate) {
        return Optional.ofNullable(
                        exchangeRate.getExchangeRateResponseDataList())
                .map(list -> list.get(0))
                .orElse(null);
    }
}
