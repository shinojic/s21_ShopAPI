package ru.shop.dao.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "product")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ProductEntity {

    @Id
    @Column(updatable = false, nullable = false, unique = true)
    private UUID id;  // hibernate самостоятельно выберет тип uuid для базы данных

    @Column(updatable = false, nullable = false)  // оставим максимальную длину в 255 символов
    private String name;

    @Column(length = 50, updatable = false, nullable = false)
    private String category;

    @Column(nullable = false, precision = 10, scale = 2)  // цена не может быть точнее копеек
    private BigDecimal price;

    @Column(name = "available_stock", nullable = false)
    private Integer availableStock;

    @Column(name = "last_update_date", nullable = false)
    private LocalDate lastUpdateDate;

    @Column(name = "supplier_id", nullable = false)
    private UUID supplierId;

    @Column(name = "image_id")
    private UUID imageId;

}