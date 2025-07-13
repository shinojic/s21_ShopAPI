package ru.shop.domain.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@Schema(description = "Объект товара")
public class ProductDto {

    @Schema(description = "Уникальный идентификатор товара",
            example = "f30ce8ab-1c41-4dc5-91d5-b565c24fd856")
    private UUID id;

    @Schema(description = "Название товара", example = "Dyson V15s Submarine")
    private String name;

    @Schema(description = "Категория товара", example = "Пылесосы")
    private String category;

    @Schema(description = "Цена товара", example = "149990.00")
    private BigDecimal price;

    @Schema(description = "Количество единиц товара в наличии", example = "15")
    private Integer availableStock;

    @Schema(description = "Дата последней закупки", example = "2025-07-03")
    private LocalDate lastUpdateDate;

    @Schema(description = "Уникальный идентификатор поставщика в таблице supplier",
            example = "bb26dbf2-4a5a-4f54-9833-a08c4899e3a2")
    private UUID supplierId;

    @Schema(description = "Уникальный идентификатор изображения товара в таблице image",
            example = "4614df5c-2022-4555-a102-aa3659488600")
    private UUID imageId;

}
