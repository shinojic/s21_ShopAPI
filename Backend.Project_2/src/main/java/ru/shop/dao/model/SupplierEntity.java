package ru.shop.dao.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.AccessLevel;

import java.util.UUID;

@Entity
@Table(name = "supplier")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class SupplierEntity {

    @Id
    @Column(updatable = false, nullable = false, unique = true)
    private UUID id;  // hibernate самостоятельно выберет тип uuid для базы данных

    @Column(length = 30, updatable = false, nullable = false)
    private String name;

    @Column(name = "address_id", nullable = false)
    private UUID addressId;

    @Column(length = 20, name = "phone_number", nullable = false)
    private String phoneNumber;  // String, так как могут быть +, -, скобки и ведущие нули

}