package integration;

import com.purchasetransaction.PurchaseTransactionServiceApplication;
import com.purchasetransaction.dto.ConvertedPurchaseTransactionResponse;
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

import java.util.Objects;
import java.util.UUID;

import static com.purchasetransaction.constant.MessageConstants.PURCHASE_CANNOT_BE_CONVERTED_MESSAGE;
import static com.purchasetransaction.constant.MessageConstants.PURCHASE_ID_NOT_FOUND_MESSAGE;
import static constant.TestContants.BRAZIL_REAL_CONVERTED_PURCHASE_AMOUNT;
import static constant.TestContants.BRAZIL_REAL_EXCHANGE_RATE;
import static constant.TestContants.COUNTRY_CURRENCY;
import static constant.TestContants.DESCRIPTION;
import static constant.TestContants.ORIGINAL_PURCHASE_AMOUNT;
import static constant.TestContants.PURCHASE_DATE;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static stub.PurchaseTransactionRequestStub.buildPurchaseTransactionRequest;
import static stub.PurchaseTransactionRequestStub.buildPurchaseWithNoExchangeRateAvailable;

@DirtiesContext
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = PurchaseTransactionServiceApplication.class,
        properties = {
                "spring.data.mongodb.host=localhost",
                "spring.data.mongodb.port=27019",
                "spring.data.mongodb.database=purchase-transaction-service",
                "de.flapdoodle.mongodb.embedded.version=5.0.5",
        }, webEnvironment = RANDOM_PORT)
public class RetrieveTransactionItTest {

    @Autowired
    private TestRestTemplate client;
    @LocalServerPort
    private int randomServerPort;

    @Test
    void retrieveTransaction() {
        final PurchaseTransactionRequest request =
                buildPurchaseTransactionRequest();
        final UUID purchaseId = preparePurchaseTransaction(request);

        final ResponseEntity<ConvertedPurchaseTransactionResponse> response =
                client.getForEntity(
                        "http://localhost:%d/purchase-transactions/%s/%s"
                                .formatted(randomServerPort, purchaseId,
                                        COUNTRY_CURRENCY),
                        ConvertedPurchaseTransactionResponse.class);

        assertSuccessfulPurchaseRetrieving(response);
    }

    @Test
    void retrieveTransaction_whenNonExistentTransactionId_thenReturnNotFound() {
        final UUID purchaseId = UUID.randomUUID();

        final ResponseEntity<ApiError> response =
                getRetrieveConvertedPurchaseTransaction(purchaseId,
                        COUNTRY_CURRENCY);

        assertFailedPurchaseRetrieving(response, PURCHASE_ID_NOT_FOUND_MESSAGE);
    }

    @Test
    void retrieveTransaction_whenInvalidCountryCurrency_thenReturnNotFound() {
        final PurchaseTransactionRequest request =
                buildPurchaseTransactionRequest();
        final UUID purchaseId = preparePurchaseTransaction(request);

        final ResponseEntity<ApiError> response =
                getRetrieveConvertedPurchaseTransaction(purchaseId,
                        "InvalidCountryCurrency");

        assertFailedPurchaseRetrieving(response,
                PURCHASE_CANNOT_BE_CONVERTED_MESSAGE);
    }

    @Test
    void retrieveTransaction_whenNoRateFoundInThePeriod_thenReturnNotFound() {
        final PurchaseTransactionRequest request =
                buildPurchaseWithNoExchangeRateAvailable();
        final UUID purchaseId = preparePurchaseTransaction(request);

        final ResponseEntity<ApiError> response = 
                getRetrieveConvertedPurchaseTransaction(purchaseId,
                        COUNTRY_CURRENCY);

        assertFailedPurchaseRetrieving(response,
                PURCHASE_CANNOT_BE_CONVERTED_MESSAGE);
    }

    private static void assertSuccessfulPurchaseRetrieving(
            ResponseEntity<ConvertedPurchaseTransactionResponse> response) {
        assertAll(() -> {
            assertNotNull(response);
            assertEquals(HttpStatus.OK, response.getStatusCode());
            final ConvertedPurchaseTransactionResponse body = response.getBody();
            assertNotNull(body);
            assertNotNull(body.getPurchaseId());
            assertEquals(DESCRIPTION, body.getDescription());
            assertEquals(PURCHASE_DATE, body.getTransactionDate());
            assertEquals(ORIGINAL_PURCHASE_AMOUNT, body.getOriginalPurchaseAmount());
            assertEquals(BRAZIL_REAL_EXCHANGE_RATE, body.getExchangeRateUsed());
            assertEquals(BRAZIL_REAL_CONVERTED_PURCHASE_AMOUNT,
                    body.getConvertedPurchaseAmount());
        });
    }

    private static void assertFailedPurchaseRetrieving(
            ResponseEntity<ApiError> response, String expectedMessage) {
        assertAll(() -> {
            assertNotNull(response);
            final ApiError body = response.getBody();
            assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
            assertNotNull(body);
            assertNotNull(body.getTimestamp());
            assertEquals(HttpStatus.NOT_FOUND, body.getStatus());
            assertEquals(expectedMessage, body.getErrorMessage());
        });
    }

    private UUID preparePurchaseTransaction(PurchaseTransactionRequest request) {
        final ResponseEntity<PurchaseTransactionResponse> response =
                client.postForEntity("http://localhost:%d/purchase-transactions"
                                .formatted(randomServerPort),
                        request, PurchaseTransactionResponse.class);
        return Objects.requireNonNull(response.getBody()).getPurchaseId();
    }

    private ResponseEntity<ApiError> getRetrieveConvertedPurchaseTransaction(
            UUID purchaseId, String countryCurrency) {
        return client.getForEntity(
                        "http://localhost:%d/purchase-transactions/%s/%s"
                                .formatted(randomServerPort, purchaseId,
                                        countryCurrency),
                        ApiError.class);
    }
}
