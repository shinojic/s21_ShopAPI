package ru.shop.controller;

import io.swagger.v3.oas.annotations.Operation;
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
import ru.shop.domain.model.SupplierDto;
import ru.shop.domain.service.address.AddressService;
import ru.shop.domain.service.supplier.SupplierService;

import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1")
public class SupplierController {

    private final SupplierService service;
    private final AddressService addressService;

    @Autowired
    public SupplierController(SupplierService supplierService, AddressService addressService) {
        this.service = supplierService;
        this.addressService = addressService;
    }

    @PostMapping("/suppliers")
    @Operation(
            summary = "Добавление нового поставщика",
            description = "Создает нового поставщика и возвращает его данные",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Поставщик успешно создан",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = SupplierDto.class))
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Некорректные данные поставщика",
                            content = @Content(mediaType = "application/json",
                                    examples = @ExampleObject(value = "{\"error\": \"Некорректные данные поставщика\"}"))
                    )
            }
    )
    public ResponseEntity<?> addSupplier(@RequestBody SupplierDto supplier) {
        if (service.add(supplier)) {
            URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(supplier.getId())
                    .toUri();
            return ResponseEntity.created(location).body(supplier);  // 201 Created
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", "Некорректные данные поставщика"));  // 400 Bad Request
        }
    }

    @PatchMapping("/supplier/{id}/address")
    @Operation(
            summary = "Обновление адреса поставщика",
            description = "Обновляет адрес поставщика по его ID и новому адресу"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Адрес успешно обновлён, возвращается обновлённый поставщик",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = SupplierDto.class)
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
    public ResponseEntity<?> changeSupplierAddress(@PathVariable UUID id,
                                                 @RequestBody AddressDto address) {
        if (service.changeAddress(addressService, id, address)) {
            SupplierDto updatedSupplier = service.getById(id);
            return ResponseEntity.ok(updatedSupplier);  // 200 OK
        } else {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "Некорректный ID поставщика или некорректный адрес"));  // 404 Not Found
        }
    }

    @DeleteMapping("/suppliers")
    @Operation(
            summary = "Удаление поставщика по ID",
            description = "Удаляет поставщика из системы по его уникальному идентификатору.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "204",
                    description = "Поставщик успешно удален. Нет содержимого в ответе.",
                    content = @Content),
            @ApiResponse(
                    responseCode = "404",
                    description = "Поставщик с указанным ID не найден.",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(value = "{\"error\": \"Некорректный ID\"}")
                    )
            )
    })
    public ResponseEntity<?> deleteSupplierById(@RequestParam UUID id) {
        if (service.deleteById(id)) {
            return ResponseEntity.noContent().build();  // 204 No Content
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "Некорректный ID"));  // 404 Not Found
        }
    }

    @GetMapping("/suppliers")
    @Operation(
            summary = "Получение списка всех поставщиков",
            description = "Возвращает список поставщиков"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Успешный ответ с списком поставщиков",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    type = "array",
                                    implementation = SupplierDto.class
                            )
                    )
            )
    })
    public ResponseEntity<?> getAllSuppliers() {
        List<SupplierDto> suppliers = service.getAll();
        return ResponseEntity.ok(suppliers);  // 200 OK
    }

    @GetMapping("/suppliers/{id}")
    @Operation(
            summary = "Получение информации о поставщике по ID",
            description = "Возвращает информацию о поставщике по его уникальному идентификатору. " +
                    "Если поставщик найден, возвращает его данные. " +
                    "Если поставщик с указанным ID не найден, возвращает ошибку."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Поставщик найден, возвращается информация о поставщике",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = SupplierDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Поставщик с таким ID не найден",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    value = "{\"error\": \"Поставщик с таким ID не найден\"}"
                            )
                    )
            )
    })
    public ResponseEntity<?> getSupplierById(@PathVariable UUID id) {
        SupplierDto supplier = service.getById(id);
        if (supplier != null) {
            return ResponseEntity.ok(supplier);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "Поставщик с таким ID не найден"));
        }
    }
}
