package ru.shop.domain.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Schema(description = "Объект адреса")
public class AddressDto {

    @Schema(description = "Уникальный идентификатор адреса",
            example = "34053e93-0892-4076-80d3-16cc962bf01c")
    private UUID id;

    @Schema(description = "Страна", example = "Belarus")
    private String country;

    @Schema(description = "Город", example = "Minsk")
    private String city;

    @Schema(description = "Улица", example = "Praspekt Nezalezhnasci")
    private String street;

}
