package org.example.client;

import org.example.dto.response.OpenApiStockResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "openApiClient", url = "${openapi.base-url}")
public interface IndexApiClient {

  @GetMapping("/getStockMarketIndex")
  OpenApiStockResponseDto getIndexData(
      @RequestParam(value="serviceKey") String serviceKey,
      @RequestParam("numOfRows") int numOfRows,
      @RequestParam("pageNo") int pageNo,
      @RequestParam("beginBasDt") String beginBasDt,
      @RequestParam("endBasDt") String endBasDt,
      @RequestParam("resultType") String resultType
  );
}
