package ru.shop.domain.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Schema(description = "Объект клиента")
public class ClientDto {

    @Schema(description = "Уникальный идентификатор клиента",
            example = "123e4567-e89b-12d3-a456-426614174000")
    private UUID id;

    @Schema(description = "Имя клиента", example = "Ivan")
    private String name;

    @Schema(description = "Фамилия клиента", example = "Dulin")
    private String surname;

    @Schema(description = "Дата рождения клиента", example = "1999-12-31")
    private LocalDate birthday;

    @Schema(description = "Пол клиента", example = "male")
    private String gender;

    @Schema(description = "Дата регистрации в приложении магазина", example = "2025-07-03")
    private LocalDate registrationDate;

    @Schema(description = "Уникальный идентификатор адреса клиента в таблице address",
            example = "34053e93-0892-4076-80d3-16cc962bf01c")
    private UUID addressId;

}
