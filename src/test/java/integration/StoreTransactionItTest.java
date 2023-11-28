package integration;

import com.purchasetransaction.PurchaseTransactionServiceApplication;
import com.purchasetransaction.dto.PurchaseTransactionRequest;
import com.purchasetransaction.dto.PurchaseTransactionResponse;
import com.purchasetransaction.dto.ApiError;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static com.purchasetransaction.constant.MessageConstants.AMOUNT_CANNOT_BE_BLANK_MESSAGE;
import static com.purchasetransaction.constant.MessageConstants.DESCRIPTION_CANNOT_BE_BLANK_MESSAGE;
import static com.purchasetransaction.constant.MessageConstants.INVALID_DESCRIPTION_MESSAGE;
import static com.purchasetransaction.constant.MessageConstants.INVALID_PURCHASE_AMOUNT_MESSAGE;
import static com.purchasetransaction.constant.MessageConstants.PURCHASE_AMOUNT_MESSAGE_IS_OUT_OF_LIMIT;
import static com.purchasetransaction.constant.MessageConstants.TRANSACTION_DATE_CANNOT_BE_BLANK_MESSAGE;
import static constant.TestContants.DESCRIPTION;
import static constant.TestContants.ORIGINAL_PURCHASE_AMOUNT;
import static constant.TestContants.PURCHASE_DATE;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static stub.PurchaseTransactionRequestStub.buildPurchaseTransactionRequest;
import static stub.PurchaseTransactionRequestStub.buildRequestWithAmountOutOfLimit;
import static stub.PurchaseTransactionRequestStub.buildRequestWithEmptyDescription;
import static stub.PurchaseTransactionRequestStub.buildRequestWithNullAmount;
import static stub.PurchaseTransactionRequestStub.buildRequestWithNullDescription;
import static stub.PurchaseTransactionRequestStub.buildRequestWithNullTransactionDate;
import static stub.PurchaseTransactionRequestStub.buildRequestWithTooLargeDescription;
import static stub.PurchaseTransactionRequestStub.buildRequestWithZeroedAmount;

@DirtiesContext
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = PurchaseTransactionServiceApplication.class,
        properties = {
                "spring.data.mongodb.host=localhost",
                "spring.data.mongodb.port=27019",
                "spring.data.mongodb.database=purchase-transaction-service",
                "de.flapdoodle.mongodb.embedded.version=5.0.5",
        }, webEnvironment = RANDOM_PORT)
public class StoreTransactionItTest {

    @Autowired
    private TestRestTemplate client;
    @LocalServerPort
    private int randomServerPort;

    @Test
    void storeTransaction() {
        final PurchaseTransactionRequest request =
                buildPurchaseTransactionRequest();

        final ResponseEntity<PurchaseTransactionResponse> response = client
                .postForEntity("http://localhost:%d/purchase-transactions"
                                .formatted(randomServerPort),
                        request, PurchaseTransactionResponse.class);

        assertSuccessfulPurchaseCreation(response);
    }

    @Test
    void storeTransaction_whenTooLargeDescription_thenReturnBadRequest() {
        final PurchaseTransactionRequest request =
                buildRequestWithTooLargeDescription();

        final ResponseEntity<ApiError> response = postFailedPurchaseCreation(
                request);

        assertFailedPurchaseCreationSingleMessage(response,
                INVALID_DESCRIPTION_MESSAGE);
    }

    @Test
    void storeTransaction_whenEmptyDescription_thenReturnBadRequest() {
        final PurchaseTransactionRequest request =
                buildRequestWithEmptyDescription();

        final ResponseEntity<ApiError> response = postFailedPurchaseCreation(
                request);

        assertFailedPurchaseCreationMultipleMessages(response,
                INVALID_DESCRIPTION_MESSAGE, DESCRIPTION_CANNOT_BE_BLANK_MESSAGE);
    }

    @Test
    void storeTransaction_whenNullDescription_thenReturnBadRequest() {
        final PurchaseTransactionRequest request =
                buildRequestWithNullDescription();

        final ResponseEntity<ApiError> response = postFailedPurchaseCreation(
                request);

        assertFailedPurchaseCreationSingleMessage(response,
                DESCRIPTION_CANNOT_BE_BLANK_MESSAGE);
    }

    @Test
    void storeTransaction_whenZeroedAmount_thenReturnBadRequest() {
        final PurchaseTransactionRequest request = buildRequestWithZeroedAmount();

        final ResponseEntity<ApiError> response = postFailedPurchaseCreation(
                request);

        assertFailedPurchaseCreationSingleMessage(response,
                INVALID_PURCHASE_AMOUNT_MESSAGE);
    }

    @Test
    void storeTransaction_whenAmountIsOutOfLimit_thenReturnBadRequest() {
        final PurchaseTransactionRequest request =
                buildRequestWithAmountOutOfLimit();

        final ResponseEntity<ApiError> response = postFailedPurchaseCreation(
                request);

        assertFailedPurchaseCreationSingleMessage(response,
                PURCHASE_AMOUNT_MESSAGE_IS_OUT_OF_LIMIT);
    }

    @Test
    void storeTransaction_whenNullPurchase_thenReturnBadRequest() {
        final PurchaseTransactionRequest request = buildRequestWithNullAmount();

        final ResponseEntity<ApiError> response = postFailedPurchaseCreation(
                request);

        assertFailedPurchaseCreationSingleMessage(response,
                AMOUNT_CANNOT_BE_BLANK_MESSAGE);
    }

    @Test
    void storeTransaction_whenNullDate_thenReturnBadRequest() {
        final PurchaseTransactionRequest request =
                buildRequestWithNullTransactionDate();

        final ResponseEntity<ApiError> response = postFailedPurchaseCreation(
                request);

        assertFailedPurchaseCreationSingleMessage(response,
                TRANSACTION_DATE_CANNOT_BE_BLANK_MESSAGE);
    }

    private ResponseEntity<ApiError> postFailedPurchaseCreation(
            PurchaseTransactionRequest request) {
        return client
                .postForEntity("http://localhost:%d/purchase-transactions"
                                .formatted(randomServerPort),
                        request, ApiError.class);
    }

    private static void assertSuccessfulPurchaseCreation(
            ResponseEntity<PurchaseTransactionResponse> response) {
        assertAll(() -> {
            assertNotNull(response);
            assertEquals(HttpStatus.OK, response.getStatusCode());
            final PurchaseTransactionResponse body = response.getBody();
            assertNotNull(body);
            assertNotNull(body.getPurchaseId());
            assertEquals(DESCRIPTION, body.getDescription());
            assertEquals(PURCHASE_DATE, body.getTransactionDate());
            assertEquals(ORIGINAL_PURCHASE_AMOUNT,
                    body.getOriginalPurchaseAmount());
        });
    }

    private static void assertFailedPurchaseCreationSingleMessage(
            ResponseEntity<ApiError> response, String expectedMessage) {
        final ApiError body = assertFailedPurchaseCreation(response);
        assertEquals(expectedMessage, body.getErrorMessage());
    }

    private static void assertFailedPurchaseCreationMultipleMessages(
            ResponseEntity<ApiError> response, String... expectedMessages) {
        final ApiError body = assertFailedPurchaseCreation(response);
        for (String expectedMessage : expectedMessages) {
            assertTrue(body.getErrorMessage().contains(expectedMessage));
        }
    }

    private static ApiError assertFailedPurchaseCreation(
            ResponseEntity<ApiError> response) {
        assertNotNull(response);
        final ApiError body = response.getBody();

        assertAll(() -> {
            assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
            assertNotNull(body);
            assertNotNull(body.getTimestamp());
            assertEquals(HttpStatus.BAD_REQUEST, body.getStatus());
        });
        return body;
    }
}
