package ru.shop.mapper;

import lombok.NoArgsConstructor;
import ru.shop.dao.model.ProductEntity;
import ru.shop.domain.model.ProductDto;

@NoArgsConstructor
public class ProductMapper {

    public ProductDto toDto(final ProductEntity entity, ProductDto dto) {
        if (entity == null || dto == null) {
            return null;
        }
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setCategory(entity.getCategory());
        dto.setPrice(entity.getPrice());
        dto.setAvailableStock(entity.getAvailableStock());
        dto.setLastUpdateDate(entity.getLastUpdateDate());
        dto.setSupplierId(entity.getSupplierId());
        dto.setImageId(entity.getImageId());
        return dto;
    }

    public ProductEntity toEntity(final ProductDto dto, ProductEntity entity) {
        if (entity == null || dto == null) {
            return null;
        }
        entity.setId(dto.getId());
        entity.setName(dto.getName());
        entity.setCategory(dto.getCategory());
        entity.setPrice(dto.getPrice());
        entity.setAvailableStock(dto.getAvailableStock());
        entity.setLastUpdateDate(dto.getLastUpdateDate());
        entity.setSupplierId(dto.getSupplierId());
        entity.setImageId(dto.getImageId());
        return entity;
    }
}
