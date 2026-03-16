package org.example.repository;

import java.time.Instant;
import java.util.List;
import org.example.entity.IndexData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface IndexDataRepository extends JpaRepository<IndexData, Long> {
  @Query("SELECT i FROM IndexData i " +
      "WHERE i.indexInfo.favorite = true " + // 💡 IndexInfo의 favorite 필드가 true인 것만!
      "AND i.baseDate IN :dates " +
      "ORDER BY i.indexInfo.id ASC, i.baseDate DESC")
  List<IndexData> findAllBaseData(
      @Param("ids") List<Long> ids,
      @Param("dates") List<Instant> dates
  );

}
