package constant;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.UUID;

public class TestContants {

    public static final String TRANSACTION_ID =
            "anyPurchaseTransactionId";
    public static final UUID PURCHASE_ID =
            UUID.fromString("550e8400-e29b-41d4-a716-446655440000");
    public static final String DESCRIPTION = "anyDescription";
    public static final String TOO_LARGE_DESCRIPTION = "This is a too long short in order to validate max size of 50 characters.";
    public static final String COUNTRY_CURRENCY = "Brazil-Real";
    public static final LocalDate PURCHASE_DATE = LocalDate.parse("2023-11-29");
    public static final LocalDate START_DATE = LocalDate.parse("2023-05-29");
    public static final BigDecimal ORIGINAL_PURCHASE_AMOUNT = BigDecimal.TEN;
    public static final BigDecimal CONVERTED_PURCHASE_AMOUNT =
            BigDecimal.valueOf(20).setScale(2, RoundingMode.HALF_EVEN);
    public static final BigDecimal EXCHANGE_RATE = BigDecimal.valueOf(2);
    public static final LocalDate RECORD_DATE = LocalDate.parse("2023-11-28");
    public static final int MAX_PERIOD_IN_MONTHS = 6;
    public static final BigDecimal EXCHANGE_RATE_FOUR_DECIMALS =
            BigDecimal.valueOf(2.2578);
    public static final BigDecimal CONVERTED_PURCHASE_AMOUNT_TWO_DECIMALS =
            BigDecimal.valueOf(22.58);
    public static final BigDecimal BRAZIL_REAL_EXCHANGE_RATE =
            BigDecimal.valueOf(5.033);
    public static final BigDecimal BRAZIL_REAL_CONVERTED_PURCHASE_AMOUNT =
            BigDecimal.valueOf(50.33).setScale(2, RoundingMode.HALF_EVEN);
    public static final LocalDate PURCHASE_DATE_WITH_NO_BRAZIL_EXCHANGE_RATE =
            LocalDate.parse("2000-11-29");
}
