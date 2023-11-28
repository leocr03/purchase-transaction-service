package stub;

import com.purchasetransaction.dto.CurrencyConversion;

import static constant.TestContants.CONVERTED_PURCHASE_AMOUNT;
import static constant.TestContants.CONVERTED_PURCHASE_AMOUNT_TWO_DECIMALS;
import static constant.TestContants.COUNTRY_CURRENCY;
import static constant.TestContants.EXCHANGE_RATE;
import static constant.TestContants.EXCHANGE_RATE_FOUR_DECIMALS;
import static constant.TestContants.ORIGINAL_PURCHASE_AMOUNT;
import static constant.TestContants.PURCHASE_DATE;

public class CurrencyConversionStub {

    public static CurrencyConversion buildCurrencyConversion() {
        return CurrencyConversion.builder()
                .countryCurrency(COUNTRY_CURRENCY)
                .purchaseDate(PURCHASE_DATE)
                .exchangeRate(EXCHANGE_RATE)
                .originalPurchaseAmount(ORIGINAL_PURCHASE_AMOUNT)
                .convertedPurchaseAmount(CONVERTED_PURCHASE_AMOUNT)
                .build();
    }

    public static CurrencyConversion buildCurrencyConversionWithTwoDecimals() {
        return CurrencyConversion.builder()
                .countryCurrency(COUNTRY_CURRENCY)
                .purchaseDate(PURCHASE_DATE)
                .exchangeRate(EXCHANGE_RATE_FOUR_DECIMALS)
                .originalPurchaseAmount(ORIGINAL_PURCHASE_AMOUNT)
                .convertedPurchaseAmount(CONVERTED_PURCHASE_AMOUNT_TWO_DECIMALS)
                .build();
    }
}
