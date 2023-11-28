package stub;

import com.purchasetransaction.dto.PurchaseTransactionRequest;

import java.math.BigDecimal;

import static constant.TestContants.DESCRIPTION;
import static constant.TestContants.ORIGINAL_PURCHASE_AMOUNT;
import static constant.TestContants.PURCHASE_DATE;
import static constant.TestContants.PURCHASE_DATE_WITH_NO_BRAZIL_EXCHANGE_RATE;
import static constant.TestContants.TOO_LARGE_DESCRIPTION;

public class PurchaseTransactionRequestStub {

    public static PurchaseTransactionRequest buildPurchaseTransactionRequest() {
        return PurchaseTransactionRequest.builder()
                .description(DESCRIPTION)
                .transactionDate(PURCHASE_DATE)
                .originalPurchaseAmount(ORIGINAL_PURCHASE_AMOUNT)
                .build();
    }

    public static PurchaseTransactionRequest buildPurchaseWithNoExchangeRateAvailable() {
        return PurchaseTransactionRequest.builder()
                .description(DESCRIPTION)
                .transactionDate(PURCHASE_DATE_WITH_NO_BRAZIL_EXCHANGE_RATE)
                .originalPurchaseAmount(ORIGINAL_PURCHASE_AMOUNT)
                .build();
    }

    public static PurchaseTransactionRequest buildRequestWithTooLargeDescription() {
        return PurchaseTransactionRequest.builder()
                .description(TOO_LARGE_DESCRIPTION)
                .transactionDate(PURCHASE_DATE)
                .originalPurchaseAmount(ORIGINAL_PURCHASE_AMOUNT)
                .build();
    }

    public static PurchaseTransactionRequest buildRequestWithNullTransactionDate() {
        return PurchaseTransactionRequest.builder()
                .description(DESCRIPTION)
                .transactionDate(null)
                .originalPurchaseAmount(ORIGINAL_PURCHASE_AMOUNT)
                .build();
    }

    public static PurchaseTransactionRequest buildRequestWithEmptyDescription() {
        return PurchaseTransactionRequest.builder()
                .description("")
                .transactionDate(PURCHASE_DATE)
                .originalPurchaseAmount(ORIGINAL_PURCHASE_AMOUNT)
                .build();
    }

    public static PurchaseTransactionRequest buildRequestWithNullDescription() {
        return PurchaseTransactionRequest.builder()
                .description(null)
                .transactionDate(PURCHASE_DATE)
                .originalPurchaseAmount(ORIGINAL_PURCHASE_AMOUNT)
                .build();
    }

    public static PurchaseTransactionRequest buildRequestWithZeroedAmount() {
        return PurchaseTransactionRequest.builder()
                .description(DESCRIPTION)
                .transactionDate(PURCHASE_DATE)
                .originalPurchaseAmount(BigDecimal.ZERO)
                .build();
    }

    public static PurchaseTransactionRequest buildRequestWithAmountOutOfLimit() {
        return PurchaseTransactionRequest.builder()
                .description(DESCRIPTION)
                .transactionDate(PURCHASE_DATE)
                .originalPurchaseAmount(BigDecimal.valueOf(0.009d))
                .build();
    }

    public static PurchaseTransactionRequest buildRequestWithNullAmount() {
        return PurchaseTransactionRequest.builder()
                .description(DESCRIPTION)
                .transactionDate(PURCHASE_DATE)
                .originalPurchaseAmount(null)
                .build();
    }
}
