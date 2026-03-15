package org.example.mapper;

import org.example.dto.data.SyncJobDto;
import org.example.entity.IntegrationLog;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface SyncJobMapper {
  @Mapping(source = "indexInfo.id", target = "indexInfoId")
  SyncJobDto toDto(IntegrationLog integrationLog);
}
