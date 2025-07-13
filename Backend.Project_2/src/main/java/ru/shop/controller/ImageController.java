package ru.shop.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.shop.domain.model.ImageDto;
import ru.shop.domain.service.image.ImageService;
import ru.shop.domain.service.product.ProductService;

import java.net.URI;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1")
public class ImageController {

    private final ImageService service;
    private final ProductService productService;

    @Autowired
    public ImageController(ImageService imageService, ProductService productService) {
        this.service = imageService;
        this.productService = productService;
    }

    @PostMapping("/images")
    @Operation(
            summary = "Добавление нового изображения",
            description = "Создает новое изображение и возвращает его уникальный идентификатор",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Изображение успешно сохранено"
                    )
            }
    )
    public ResponseEntity<?> addImage(@RequestParam UUID id, @RequestParam byte[] bytes) {
        service.add(bytes, id);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(id)
                .toUri();
        return ResponseEntity.created(location).body(id);  // 201 Created
    }

    @PatchMapping("/images/{id}")
    @Operation(
            summary = "Изменение изображения по ID",
            description = "Заменяет массив байт изображения на новый"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Изображение успешно изменено, возвращается уникальный идентификатор"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Некорректный ID изображения",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    value = "{\"error\": \"Некорректный ID\"}"
                            )
                    )
            )
    })
    public ResponseEntity<?> changeImage(@PathVariable UUID id, @RequestParam byte[] bytes) {
        if (service.changeBytesById(bytes, id)) {
            return ResponseEntity.ok(id);  // 200 ОК
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "Некорректный ID"));  // 404 Not Found
        }
    }

    @DeleteMapping("/images")
    @Operation(
            summary = "Удаление изображения по ID",
            description = "Удаляет изображения с указанным ID."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "204",
                    description = "Изображение успешно удалено"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Изображение с таким ID не найдено",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    value = "{\"error\": \"Изображение с таким ID не найдено\"}"
                            )
                    )
            )
    })
    public ResponseEntity<?> deleteImageById(@RequestParam UUID id) {
        if (service.deleteById(id)) {
            return ResponseEntity.noContent().build();  // 204 No content
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "Изображение с таким ID не найдено"));  // 404 Not found
        }
    }

    @GetMapping("/images/{id}")
    @Operation(
            summary = "Получение изображения по ID",
            description = "Возвращает изображение по его уникальному идентификатору. " +
                    "Если изображение найдено, загружает его. " +
                    "Если изображение с указанным ID не найдено, возвращает ошибку."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Изображение найдено, загружается файл"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Изображение с таким ID не найдено",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    value = "{\"error\": \"Изображение с таким ID не найдено\"}"
                            )
                    )
            )
    })
    public ResponseEntity<?> getImageById(@PathVariable UUID id) {
        ImageDto image = service.getById(id);
        if (image != null) {
            return ResponseEntity.ok()
                    // указывает, что это бинарный файл
                    .header("Content-Type", "application/octet-stream")

                    // заставляет браузер скачать файл, а не отображать его
                    .header("Content-Disposition",
                            "attachment; filename=\"image_" + image.getId() + ".bin\"")

                    .body(image.getBytes());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "Изображение с таким ID не найдено"));  // 404 Not found
        }
    }

    @GetMapping("/images/products/{id}")
    @Operation(
            summary = "Получение изображения по ID товара",
            description = "Возвращает изображение по уникальному идентификатору товара. " +
                    "Если изображение найдено, загружает его. " +
                    "Если изображение с указанным ID товара не найдено, возвращает ошибку."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Изображение найдено, загружается файл"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Изображение с таким ID товара не найдено",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    value = "{\"error\": \"Изображение с таким ID товара не найдено\"}"
                            )
                    )
            )
    })
    public ResponseEntity<?> getImageByProductId(@PathVariable UUID productId) {
        ImageDto image = service.getByProductId(productId, productService);
        if (image != null) {
            return ResponseEntity.ok()
                    .header("Content-Type", "application/octet-stream")
                    .header("Content-Disposition",
                            "attachment; filename=\"image_" + image.getId() + ".bin\"")
                    .body(image.getBytes());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "Изображение с таким ID товара не найдено"));  // 404 Not found
        }
    }
}
