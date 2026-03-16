package org.example.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.example.dto.response.FavoritePerformanceResponse;
import org.example.dto.response.RankedIndexPerformanceDto;
import org.example.dto.response.RankedIndexPerformanceDto.IndexPerformanceDto;
import org.example.entity.IndexData;
import org.example.repository.IndexDataRepository;
import org.example.repository.IndexInfoRepository;
import org.h2.index.Index;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class IndexDataService {
  private final IndexDataRepository indexDataRepository;
  private final IndexInfoRepository indexInfoRepository;

  public List<FavoritePerformanceResponse> getFavoritePerformances(String periodType) {
    ZoneId zoneId = ZoneId.of("Asia/Seoul");
    Instant today = LocalDate.now(zoneId).atStartOfDay(zoneId).toInstant();
    Instant baseDate;

    List<Long> favoriteIndexIds = indexInfoRepository.findFavoriteIndexIds();

    if(favoriteIndexIds.isEmpty()) {
      return Collections.emptyList();
    }

    switch(periodType.toUpperCase()) {
      case "DAILY" :
      default:
        baseDate = today.minus(1, ChronoUnit.DAYS);
        break;
      case "WEEKLY" :
        baseDate = today.minus(7, ChronoUnit.DAYS);
        break;
      case "MONTHLY" :
        baseDate = today.minus(30,ChronoUnit.DAYS);
        break;
    }

    List<Instant> baseDates = List.of(today,baseDate);
    List<IndexData> dataList = indexDataRepository.findAllBaseData(favoriteIndexIds,baseDates);


    Map<Long, List<IndexData>> groupedData = dataList.stream()
        .collect(Collectors.groupingBy(data -> data.getIndexInfo().getId()));

    List<IndexPerformanceDto> performances = groupedData.entrySet().stream()
        .map(entry-> calculatePerformance(entry.getKey(), entry.getValue()))
        .toList();
    AtomicInteger rankCounter = new AtomicInteger(1);

    return dataList.stream()
        .collect(Collectors.groupingBy(data -> data.getIndexInfo().getId()))
        .entrySet().stream()
        .map(entry -> {
          Long indexId = entry.getKey();
          List<IndexData> indexData = entry.getValue();

          if(indexData.size() < 2) return null;

          IndexData current = indexData.get(0);
          IndexData before = indexData.get(1);

          BigDecimal versus = current.getClosePrice().subtract(before.getClosePrice());
          BigDecimal fluctuationRate = versus.divide(before.getClosePrice(), 4, RoundingMode.HALF_UP).multiply(new BigDecimal(100));

          return new FavoritePerformanceResponse(
              indexId,
              current.getIndexInfo().getCategoryName(),
              current.getIndexInfo().getIndexName(),
              versus,
              fluctuationRate,
              current.getClosePrice(),
              before.getClosePrice()
          );
        })
        .filter(Objects::nonNull)
        .sorted((a,b) -> b.fluctuationRate().compareTo(a.fluctuationRate()))
        .toList();
  }

  public IndexPerformanceDto calculatePerformance(Long indexId, List<IndexData> dataList){
    if(dataList == null || dataList.isEmpty()) {
      throw new IllegalArgumentException("해당 지수의 데이터가 존재하지 않습니다.");
    }

    IndexData todayData = dataList.get(0);
    BigDecimal todayClosePrice = todayData.getClosePrice();
    BigDecimal yesterdayClosePrice = (dataList.size() > 1) ? dataList.get(1).getClosePrice() : todayClosePrice;

    // 등락폭 계산 (오늘 종가 - 어제 종가)
    BigDecimal priceDiff = todayClosePrice.subtract(yesterdayClosePrice);


    // 등락률 계산 ((등락폭 / 어제 종가) * 100)
    BigDecimal fluctuationRate = (yesterdayClosePrice.compareTo(BigDecimal.ZERO) == 0)
        ? BigDecimal.ZERO // 참이면 등락률 0
        : priceDiff.divide(yesterdayClosePrice, 4, RoundingMode.HALF_UP) // 거짓이면 반올림 해서 소수점 4자리까지
        .multiply(BigDecimal.valueOf(100));

    String indexName = todayData.getIndexInfo().getIndexName();
    String indexClassification = todayData.getIndexInfo().getCategoryName();

    return new IndexPerformanceDto(
        indexId,
        indexClassification,
        indexName,
        priceDiff,
        fluctuationRate,
        todayClosePrice,
        yesterdayClosePrice
    );

  }
}
