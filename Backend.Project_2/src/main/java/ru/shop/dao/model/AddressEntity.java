package ru.shop.dao.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "address")
@Getter
@NoArgsConstructor(access= AccessLevel.PRIVATE, force = true)
@AllArgsConstructor
public class AddressEntity {

    @Id
    @Column(updatable = false, nullable = false, unique = true)
    private final UUID id;  // hibernate самостоятельно выберет тип uuid для базы данных

    @Column(length = 100, updatable = false, nullable = false)
    private final String country;

    @Column(length = 100, updatable = false, nullable = false)
    private final String city;

    @Column(length = 100, updatable = false, nullable = false)
    private final String street;

}