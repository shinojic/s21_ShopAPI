package ru.shop.mapper;

import lombok.NoArgsConstructor;
import ru.shop.dao.model.ClientEntity;
import ru.shop.domain.model.ClientDto;

@NoArgsConstructor
public class ClientMapper {

    public ClientDto toDto(final ClientEntity entity, ClientDto dto) {
        if (entity == null || dto == null) {
            return null;
        }
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setSurname(entity.getSurname());
        dto.setGender(entity.getGender());
        dto.setBirthday(entity.getBirthday());
        dto.setRegistrationDate(entity.getRegistrationDate());
        dto.setAddressId(entity.getAddressId());
        return dto;
    }

    public ClientEntity toEntity(final ClientDto dto, ClientEntity entity) {
        if (entity == null || dto == null) {
            return null;
        }
        entity.setId(dto.getId());
        entity.setName(dto.getName());
        entity.setSurname(dto.getSurname());
        entity.setGender(dto.getGender());
        entity.setBirthday(dto.getBirthday());
        entity.setRegistrationDate(dto.getRegistrationDate());
        entity.setAddressId(dto.getAddressId());
        return entity;
    }
}
