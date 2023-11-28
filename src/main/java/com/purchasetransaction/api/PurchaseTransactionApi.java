package com.purchasetransaction.api;

import com.purchasetransaction.dto.ConvertedPurchaseTransactionResponse;
import com.purchasetransaction.dto.PurchaseTransactionRequest;
import com.purchasetransaction.dto.PurchaseTransactionResponse;
import com.purchasetransaction.dto.ApiError;
import com.purchasetransaction.service.PurchaseTransactionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/purchase-transactions")
public class PurchaseTransactionApi {

    private final PurchaseTransactionService purchaseTransactionService;

    @Operation(summary = "Store a purchase transaction.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "The purchase transaction has been successfully stored.",
                    content = {
                            @Content(mediaType = APPLICATION_JSON_VALUE, schema =
                            @Schema(implementation = PurchaseTransactionResponse.class))
                    }),
            @ApiResponse(responseCode = "400",
                    description = "The purchase transaction cannot be stored because it was informed invalid fields.",
                    content = {
                            @Content(mediaType = APPLICATION_JSON_VALUE, schema =
                            @Schema(implementation = ApiError.class))
                    }),
    })
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<PurchaseTransactionResponse> storePurchaseTransaction(
            @Valid @RequestBody PurchaseTransactionRequest request) {
        log.info("Storing purchase transaction. request={}", request);

        final PurchaseTransactionResponse savedPurchaseTransaction =
                purchaseTransactionService.savePurchaseTransaction(request);

        log.info("Purchase transaction stored. savedPurchaseTransaction={}",
                savedPurchaseTransaction);
        return ResponseEntity.ok(savedPurchaseTransaction);
    }

    @Operation(summary = "Retrieve a purchase transaction in a specified country's currency.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "The purchase transaction has been retrieved and converted successfully.",
                    content = {
                            @Content(mediaType = APPLICATION_JSON_VALUE, schema =
                            @Schema(implementation = ConvertedPurchaseTransactionResponse.class))
                    }),
            @ApiResponse(responseCode = "400",
                    description = "The purchase transaction cannot be retrieved because it was informed invalid fields.",
                    content = {
                            @Content(mediaType = APPLICATION_JSON_VALUE, schema =
                            @Schema(implementation = ApiError.class))
                    }),
            @ApiResponse(responseCode = "404",
                    description = "The purchase transaction cannot be retrieved because the purchaseId or countryCurrency has not been found.",
                    content = {
                            @Content(mediaType = APPLICATION_JSON_VALUE, schema =
                            @Schema(implementation = ApiError.class))
                    }),
    })
    @RequestMapping(path = "/{purchaseId}/{countryCurrency}",
            method = RequestMethod.GET)
    public ResponseEntity<ConvertedPurchaseTransactionResponse> retrievePurchaseTransaction(
            @Schema(description = "The unique identifier of the purchase transaction. It's in UUID format.",
                    example = "550e8400-e29b-41d4-a716-446655440000")
            @PathVariable("purchaseId")
            UUID purchaseId,
            @Schema(description = "Country and currency in which you want to convert the purchase amount. The format is \"Country-Currency\".",
                    example = "Brazil-Real")
            @PathVariable("countryCurrency")
            String countryCurrency) {
        log.info("Retrieving purchase transaction. purchaseId={}, " +
                "countryCurrency={}", purchaseId, countryCurrency);

        final ConvertedPurchaseTransactionResponse retrievedPurchasedTransaction =
                purchaseTransactionService.retrievePurchaseTransaction(
                        purchaseId, countryCurrency);

        log.info("Purchase transaction retrieved. retrievedPurchasedTransaction={}",
                retrievedPurchasedTransaction);
        return ResponseEntity.ok(retrievedPurchasedTransaction);
    }
}
