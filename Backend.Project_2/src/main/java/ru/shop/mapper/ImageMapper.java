package ru.shop.mapper;

import lombok.NoArgsConstructor;
import ru.shop.dao.model.ImageEntity;
import ru.shop.domain.model.ImageDto;

@NoArgsConstructor
public class ImageMapper {

    public ImageDto toDto(final ImageEntity entity, ImageDto dto) {
        if (entity == null || dto == null) {
            return null;
        }
        dto.setId(entity.getId());
        dto.setBytes(entity.getBytes());
        return dto;
    }

    public ImageEntity toEntity(final ImageDto dto, ImageEntity entity) {
        if (entity == null || dto == null) {
            return null;
        }
        entity.setId(dto.getId());
        entity.setBytes(dto.getBytes());
        return entity;
    }
}
