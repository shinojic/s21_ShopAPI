package ru.shop.mapper;

import lombok.NoArgsConstructor;
import ru.shop.dao.model.SupplierEntity;
import ru.shop.domain.model.SupplierDto;

@NoArgsConstructor
public class SupplierMapper {

    public SupplierDto toDto(final SupplierEntity entity, SupplierDto dto) {
        if (entity == null || dto == null) {
            return null;
        }
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setAddressId(entity.getAddressId());
        dto.setPhoneNumber(entity.getPhoneNumber());
        return dto;
    }

    public SupplierEntity toEntity(final SupplierDto dto, SupplierEntity entity) {
        if (entity == null || dto == null) {
            return null;
        }
        entity.setId(dto.getId());
        entity.setName(dto.getName());
        entity.setAddressId(dto.getAddressId());
        entity.setPhoneNumber(dto.getPhoneNumber());
        return entity;
    }
}
