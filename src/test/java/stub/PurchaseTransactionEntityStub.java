package stub;

import com.purchasetransaction.entity.PurchaseTransaction;

import static constant.TestContants.DESCRIPTION;
import static constant.TestContants.ORIGINAL_PURCHASE_AMOUNT;
import static constant.TestContants.PURCHASE_DATE;
import static constant.TestContants.PURCHASE_ID;
import static constant.TestContants.TRANSACTION_ID;

public class PurchaseTransactionEntityStub {

    public static PurchaseTransaction buildPurchaseTransactionEntity() {
        return new PurchaseTransaction(TRANSACTION_ID, PURCHASE_ID, DESCRIPTION,
                PURCHASE_DATE, ORIGINAL_PURCHASE_AMOUNT);
    }

    public static PurchaseTransaction buildNotPersistedPurchaseEntity() {
        return new PurchaseTransaction(null, PURCHASE_ID, DESCRIPTION,
                PURCHASE_DATE, ORIGINAL_PURCHASE_AMOUNT);
    }
}
