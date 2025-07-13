package ru.shop.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.shop.domain.model.AddressDto;
import ru.shop.domain.model.ClientDto;
import ru.shop.domain.service.address.AddressService;
import ru.shop.domain.service.client.ClientService;

import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1")
public class ClientController {

    private final ClientService clientService;
    private final AddressService addressService;

    @Autowired
    public ClientController(ClientService clientService, AddressService addressService) {
        this.clientService = clientService;
        this.addressService = addressService;
    }

    @PostMapping("/clients")
    @Operation(
            summary = "Добавление нового клиента",
            description = "Создает нового клиента и возвращает его данные",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Клиент успешно создан",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ClientDto.class))
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Некорректные данные клиента",
                            content = @Content(mediaType = "application/json",
                                    examples = @ExampleObject(value = "{\"error\": \"Некорректные данные клиента\"}"))
                    )
            }
    )
    public ResponseEntity<?> addClient(@RequestBody ClientDto client) {
        if (clientService.add(client)) {
            URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(client.getId())
                    .toUri();
            return ResponseEntity.created(location).body(client);  // 201 Created
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", "Некорректные данные клиента"));  // 400 Bad Request
        }
    }

    @DeleteMapping("/clients")
    @Operation(
            summary = "Удаление клиента по ID",
            description = "Удаляет клиента из системы по его уникальному идентификатору.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "204",
                    description = "Клиент успешно удален. Нет содержимого в ответе.",
                    content = @Content),
            @ApiResponse(
                    responseCode = "404",
                    description = "Клиент с указанным ID не найден.",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(value = "{\"error\": \"Некорректный ID\"}")
                    )
            )
    })
    public ResponseEntity<?> deleteClientById(@RequestParam UUID id) {
        if (clientService.deleteById(id)) {
            return ResponseEntity.noContent().build();  // 204 No Content
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "Некорректный ID"));  // 404 Not Found
        }
    }

    @GetMapping("/clients/search")
    @Operation(
            summary = "Поиск клиентов по имени и фамилии",
            description = "Возвращает список клиентов, соответствующих заданным имени и фамилии. " +
                    "Если клиентов с такими данными нет, возвращается пустой список."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Успешный ответ с списком клиентов",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    type = "array",
                                    implementation = ClientDto.class
                            )
                    )
            )
    })
    public ResponseEntity<List<ClientDto>> getClientByNameAndSurname(@RequestParam String firstName,
                                                       @RequestParam String lastName) {
        List<ClientDto> clients = clientService.getByNameAndSurname(firstName, lastName);
        return ResponseEntity.ok(clients);  // 200 OK
    }

    @GetMapping("/clients")
    @Operation(
            summary = "Получение списка всех клиентов",
            description = "Возвращает список клиентов с возможностью указания лимита и смещения для пагинации. " +
                    "Если параметры не указаны, возвращается полный список."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Успешный ответ с списком клиентов",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    type = "array",
                                    implementation = ClientDto.class
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Некорректные значения limit / offset",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(value = "{\"error\": \"Некорректные limit или offset (< 1)\"}")
                    )
            )
    })
    public ResponseEntity<?> getAllClients(
            @Parameter(
                    description = "Ограничение количества возвращаемых клиентов (опционально)",
                    required = false,
                    example = "10"
            )
            @RequestParam(required = false) Integer limit,
            @Parameter(
                    description = "Смещение (отступ) для пагинации (опционально)",
                    required = false,
                    example = "5"
            )
            @RequestParam(required = false) Integer offset) {
        try {
            List<ClientDto> clients = clientService.getAll(limit, offset);
            return ResponseEntity.ok(clients);  // 200 OK
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "Некорректные limit или offset (< 1)"));  // 404 Not Found
        }

    }

    @PatchMapping("/clients/{id}/address")
    @Operation(
            summary = "Обновление адреса клиента",
            description = "Обновляет адрес клиента по его ID и новому адресу"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Адрес успешно обновлён, возвращается обновлённый клиент",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ClientDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Клиент с указанным ID не найден или адрес некорректен",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    value = "{\"error\": \"Некорректный ID клиента или некорректный адрес\"}"
                            )
                    )
            )
    })
    public ResponseEntity<?> changeClientAddress(@PathVariable UUID id,
                                                 @RequestBody AddressDto address) {
        if (clientService.changeAddress(addressService, id, address)) {
            ClientDto updatedClient = clientService.getById(id);
            return ResponseEntity.ok(updatedClient);  // 200 OK
        } else {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "Некорректный ID клиента или некорректный адрес"));  // 404 Not Found
        }
    }

}
