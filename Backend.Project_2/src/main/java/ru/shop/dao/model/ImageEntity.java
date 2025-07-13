package ru.shop.dao.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "image")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ImageEntity {

    @Id
    @Column(updatable = false, nullable = false, unique = true)
    private UUID id;  // hibernate самостоятельно выберет тип uuid для базы данных

    @Lob  // хранится как Large Object (LOB), в базе данных будет тип BYTEA
    @Column(nullable = false)
    private byte[] bytes;

}