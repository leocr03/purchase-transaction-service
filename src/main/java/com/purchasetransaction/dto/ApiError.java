package com.purchasetransaction.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.ZonedDateTimeSerializer;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;

@Data
@AllArgsConstructor
public class ApiError {

    @Schema(description = "The HTTP status returned.", example = "BAD_REQUEST")
    private HttpStatus status;

    @Schema(description = "The date time when the error was identified.",
            example = "2023-12-01T20:03:09.252Z")
    @JsonSerialize(using = ZonedDateTimeSerializer.class)
    private ZonedDateTime timestamp;

    @Schema(description = "The error message collected when it occurred.",
            example = "Purchase of diesel.")
    private String errorMessage;
}
