package stub;

import com.purchasetransaction.dto.ConvertedPurchaseTransactionResponse;

import static constant.TestContants.CONVERTED_PURCHASE_AMOUNT;
import static constant.TestContants.DESCRIPTION;
import static constant.TestContants.EXCHANGE_RATE;
import static constant.TestContants.ORIGINAL_PURCHASE_AMOUNT;
import static constant.TestContants.PURCHASE_DATE;
import static constant.TestContants.PURCHASE_ID;

public class ConvertedPurchaseTransactionResponseStub {

    public static ConvertedPurchaseTransactionResponse buildConvertedPurchaseResponse() {
        return ConvertedPurchaseTransactionResponse.builder()
                .purchaseId(PURCHASE_ID)
                .description(DESCRIPTION)
                .transactionDate(PURCHASE_DATE)
                .originalPurchaseAmount(ORIGINAL_PURCHASE_AMOUNT)
                .exchangeRateUsed(EXCHANGE_RATE)
                .convertedPurchaseAmount(CONVERTED_PURCHASE_AMOUNT)
                .build();
    }
}
