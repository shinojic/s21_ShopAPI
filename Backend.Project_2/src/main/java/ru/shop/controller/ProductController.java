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
import ru.shop.domain.model.ProductDto;
import ru.shop.domain.service.product.ProductService;

import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1")
public class ProductController {

    private final ProductService service;

    @Autowired
    public ProductController(ProductService productService) {
        this.service = productService;
    }

    @PostMapping("/products")
    @Operation(
            summary = "Добавление нового товара",
            description = "Создает новый товар и возвращает его данные",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Товар успешно создан",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ProductDto.class))
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Некорректные данные клиента",
                            content = @Content(mediaType = "application/json",
                                    examples = @ExampleObject(value = "{\"error\": \"Некорректные данные клиента\"}"))
                    )
            }
    )
    public ResponseEntity<?> addProduct(@RequestBody ProductDto product) {
        if (service.add(product)) {
            URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(product.getId())
                    .toUri();
            return ResponseEntity.created(location).body(product);  // 201 Created
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", "Некорректные данные клиента"));  // 400 Bad Request
        }
    }

    @PatchMapping("/products/{id}/available-stock")
    @Operation(
            summary = "Уменьшение доступного количества товара",
            description = "Уменьшает количество товара на складе по его ID на указанное количество. " +
                    "При успешном уменьшении возвращает обновлённый товар. " +
                    "Если ID некорректен или количество недопустимо, возвращает ошибку."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Количество товара успешно уменьшено, возвращается обновлённый товар",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ProductDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Некорректный ID товара",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    value = "{\"error\": \"Некорректный ID\"}"
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Некорректное количество единиц товара - " +
                            "введенное число должно быть > 0 и меньше текущего количества товара на складе",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    value = "{\"error\": \"Некорректное количество единиц товара\"}"
                            )
                    )
            )
    })
    public ResponseEntity<?> reduceAmountOfProduct(@PathVariable UUID id,
                                                     @RequestParam Integer amount) {
        try {
            if (service.reduceByAmount(id, amount)) {
                ProductDto product = service.getById(id);
                return ResponseEntity.ok(product);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of("error", "Некорректный ID"));  // 404 Not Found
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", "Некорректное количество единиц товара"));  // 400 Bad Request
        }
    }

    @GetMapping("/products/{id}")
    @Operation(
            summary = "Получение информации о товаре по ID",
            description = "Возвращает информацию о товаре по его уникальному идентификатору. " +
                    "Если товар найден, возвращает его данные. " +
                    "Если товар с указанным ID не найден, возвращает ошибку."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Товар найден, возвращается информация о товаре",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ProductDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Товар с таким ID не найден",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    value = "{\"error\": \"Товар с таким ID не найден\"}"
                            )
                    )
            )
    })
    public ResponseEntity<?> getProductById(@PathVariable UUID id) {
        ProductDto product = service.getById(id);
        if (product != null) {
            return ResponseEntity.ok(product);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "Товар с таким ID не найден"));
        }
    }


    @GetMapping("/products")
    @Operation(
            summary = "Получение списка всех товаров",
            description = "Возвращает список всех доступных товаров. " +
                    "Если товаров нет, возвращается пустой список."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Успешное получение списка товаров",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    type = "array",
                                    implementation = ProductDto.class
                            )
                    )
            )
    })
    public ResponseEntity<List<ProductDto>> getAllProducts() {
        List<ProductDto> products = service.getAll();
        return ResponseEntity.ok(products);
    }

    @DeleteMapping("/products")
    @Operation(
            summary = "Удаление товара по ID",
            description = "Удаляет товар с указанным ID."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "204",
                    description = "Товар успешно удален"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Товар с таким ID не найден",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    value = "{\"error\": \"Товар с таким ID не найден\"}"
                            )
                    )
            )
    })
    public ResponseEntity<?> deleteProductById(@RequestParam UUID productId) {
        if (service.deleteById(productId)) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "Товар с таким ID не найден"));
        }
    }
}
