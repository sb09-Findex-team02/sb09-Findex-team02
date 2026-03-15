package org.example.service;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.client.IndexApiClient;
import org.example.dto.response.OpenApiStockResponseDto;
import org.example.dto.response.OpenApiStockResponseDto.Item;
import org.example.entity.IndexInfo;
import org.example.entity.IntegrationLog;
import org.example.entity.type.JobType;
import org.example.entity.type.StatusType;
import org.example.repository.IndexInfoRepository;
import org.example.repository.IntegrationLogRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class IntegrationService {
  private final IntegrationLogRepository integrationLogRepository;
  private final IndexInfoRepository indexInfoRepository;
  private final IndexApiClient indexApiClient;
  private final IndexInfoService indexInfoService;
  private final IndexDataService indexDataService;

  @Value("${openapi.service-key}")
  private String serviceKey;
  private static final DateTimeFormatter YYYYMMDD = DateTimeFormatter.ofPattern("yyyyMMdd");
  private static final int PAGE_SIZE = 100;

  //자수 정보 연동
  public void syncData(String startDate, String endDate) {
    OpenApiStockResponseDto response =
        indexApiClient.getIndexData(
            serviceKey, 1000, 1, startDate, endDate, "json"
        );
    Map<String, Item> uniqueIndices = response.response().body().items().item().stream()
        .collect(Collectors.toMap(
            Item::indexName, item -> item, (oldValue, newValue) -> newValue
        ));
    uniqueIndices.values().forEach(item -> {
      try {
        IndexInfo indexInfo = processIndexInfo(item);
        saveHistory(indexInfo, StatusType.success, "정상 연동");
      } catch (Exception e) {
        indexInfoRepository.findById(item.indexName()).ifPresent(info ->
            saveHistory(info, StatusType.fail, e.getMessage())
        );
      }
    });
  }

  private IndexInfo processIndexInfo(Item item) {
    IndexInfo indexInfo = indexInfoRepository.findById(item.indexName())
        .orElse(new IndexInfo(item.indexName()));
    Instant baseData = parseToInstant(item.dataBaseDate());
    BigDecimal baseIndex = new BigDecimal(String.valueOf(item.baseIndex()));
    Integer componentCount = item.componentCount();
    indexInfo.setIndexDetails(baseData, baseIndex, componentCount);
    return indexInfoRepository.save(indexInfo);
  }

  private void saveHistory(IndexInfo indexInfo, StatusType status, String msg) {
    IntegrationLog log = new IntegrationLog(indexInfo, JobType.index_info, status);
    log.setIntegrationLogDetails(null, Instant.now(), "SYSTEM", Instant.now());
    historyRepository.save(log);
  }

  private Instant parseToInstant(String dateStr) {
    try {
      return LocalDate.parse(dateStr, DateTimeFormatter.ofPattern("yyyyMMdd"))
          .atStartOfDay(ZoneId.systemDefault())
          .toInstant();
    } catch (Exception e) {
      return Instant.now();
    }
  }
}