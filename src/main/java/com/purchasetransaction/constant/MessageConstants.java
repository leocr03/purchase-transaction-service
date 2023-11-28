package com.purchasetransaction.constant;

public class MessageConstants {

    public static final String THE_FIELD_CANNOT_BE_BLANK =
            "The field cannot be blank.";
    public static final String INVALID_DESCRIPTION_MESSAGE =
            "Field description: The size cannot exceed the size of 50 characters neither be zeroed.";
    public static final String DESCRIPTION_CANNOT_BE_BLANK_MESSAGE =
            "Field description: " + THE_FIELD_CANNOT_BE_BLANK;
    public static final String INVALID_PURCHASE_AMOUNT_MESSAGE =
            "Field originalPurchaseAmount: It must be greater than 0.00.";
    public static final String PURCHASE_AMOUNT_MESSAGE_IS_OUT_OF_LIMIT =
            "Field originalPurchaseAmount: The numeric value is out of limit";
    public static final String AMOUNT_CANNOT_BE_BLANK_MESSAGE =
            "Field originalPurchaseAmount: " + THE_FIELD_CANNOT_BE_BLANK;
    public static final String TRANSACTION_DATE_CANNOT_BE_BLANK_MESSAGE =
            "Field transactionDate: " + THE_FIELD_CANNOT_BE_BLANK;
    public static final String PURCHASE_ID_NOT_FOUND_MESSAGE =
            "The purchaseId has not been found.";
    public static final String PURCHASE_CANNOT_BE_CONVERTED_MESSAGE =
            "The purchase cannot be converted to the target currency.";
    public static final String INTERNAL_ERROR_MESSAGE = "An internal error has occurred. Check the logs to get more details.";
}
