package stub;

import com.purchasetransaction.dto.PurchaseTransactionResponse;

import static constant.TestContants.DESCRIPTION;
import static constant.TestContants.ORIGINAL_PURCHASE_AMOUNT;
import static constant.TestContants.PURCHASE_DATE;
import static constant.TestContants.PURCHASE_ID;

public class PurchaseTransactionResponseStub {

    public static PurchaseTransactionResponse buildPurchaseTransactionResponse() {
        return PurchaseTransactionResponse.builder()
                .purchaseId(PURCHASE_ID)
                .description(DESCRIPTION)
                .transactionDate(PURCHASE_DATE)
                .originalPurchaseAmount(ORIGINAL_PURCHASE_AMOUNT)
                .build();
    }
}
