package integration;

import com.purchasetransaction.PurchaseTransactionServiceApplication;
import com.purchasetransaction.dto.ApiError;
import com.purchasetransaction.dto.PurchaseTransactionRequest;
import com.purchasetransaction.dto.PurchaseTransactionResponse;
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

import static com.purchasetransaction.constant.MessageConstants.INTERNAL_ERROR_MESSAGE;
import static constant.TestContants.COUNTRY_CURRENCY;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static stub.PurchaseTransactionRequestStub.buildPurchaseTransactionRequest;

@DirtiesContext
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = PurchaseTransactionServiceApplication.class,
        properties = {
                "spring.data.mongodb.host=localhost",
                "spring.data.mongodb.port=27019",
                "spring.data.mongodb.database=purchase-transaction-service",
                "de.flapdoodle.mongodb.embedded.version=5.0.5",
                "client.fiscal.data.treasury.url=https://api.fiscaldata.treasury.gov/services/api/invalid_api"
        }, webEnvironment = RANDOM_PORT)
public class ExternalApiExceptionItTest {

    @Autowired
    private TestRestTemplate client;
    @LocalServerPort
    private int randomServerPort;

    @Test
    void storeTransaction_whenApiIsUnavailable_thenReturnInternalError() {
        final PurchaseTransactionRequest request = buildPurchaseTransactionRequest();
        final UUID purchaseId = preparePurchaseTransaction(request);

        final ResponseEntity<ApiError> response =
                client.getForEntity(
                        "http://localhost:%d/purchase-transactions/%s/%s"
                                .formatted(randomServerPort, purchaseId,
                                        COUNTRY_CURRENCY),
                        ApiError.class);

        assertInternalError(response);
    }

    private UUID preparePurchaseTransaction(PurchaseTransactionRequest request) {
        final ResponseEntity<PurchaseTransactionResponse> response =
                client.postForEntity("http://localhost:%d/purchase-transactions"
                                .formatted(randomServerPort),
                        request, PurchaseTransactionResponse.class);
        return Objects.requireNonNull(response.getBody()).getPurchaseId();
    }

    private static void assertInternalError(
            ResponseEntity<ApiError> response) {
        assertAll(() -> {
            assertNotNull(response);
            final ApiError body = response.getBody();
            assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,
                    response.getStatusCode());
            assertNotNull(body);
            assertNotNull(body.getTimestamp());
            assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,
                    body.getStatus());
            assertEquals(INTERNAL_ERROR_MESSAGE, body.getErrorMessage());
        });
    }
}
