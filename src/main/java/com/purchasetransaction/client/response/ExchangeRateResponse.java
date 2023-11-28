package com.purchasetransaction.client.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude()
public class ExchangeRateResponse {

    @JsonProperty("data")
    List<ExchangeRateResponseData> exchangeRateResponseDataList;
}
