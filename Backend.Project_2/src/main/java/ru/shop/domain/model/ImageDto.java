package ru.shop.domain.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ImageDto {

    @Schema(description = "Уникальный идентификатор изображения",
            example = "66263b6f-10c7-4af5-ba50-2de9765eea33")
    private UUID id;

    @Schema(description = "Изображение в формате массива байт")
    private byte[] bytes;

}
