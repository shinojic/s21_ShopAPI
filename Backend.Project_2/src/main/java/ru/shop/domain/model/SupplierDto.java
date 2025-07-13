package ru.shop.domain.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Schema(description = "Объект поставщика")
public class SupplierDto {

    @Schema(description = "Уникальный идентификатор поставщика",
            example = "4614df5c-2022-4555-a102-aa3659488600")
    private UUID id;

    @Schema(description = "Имя поставщика", example = "DELTA")
    private String name;

    @Schema(description = "Уникальный идентификатор адреса поставщика в таблице address",
            example = "df394571-8e96-4d02-9914-8ea963bd52fd")
    private UUID addressId;

    @Schema(description = "Телефонный номер поставщика", example = "8 (800) 500-21-68")
    private String phoneNumber;

}
