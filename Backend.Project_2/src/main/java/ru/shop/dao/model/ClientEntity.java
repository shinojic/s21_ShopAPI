package ru.shop.dao.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "client")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ClientEntity {

    @Id
    @Column(updatable = false, nullable = false, unique = true)
    private UUID id;  // hibernate самостоятельно выберет тип uuid для базы данных

    @Column(length = 30, updatable = false, nullable = false)
    private String name;

    @Column(length = 50, updatable = false, nullable = false)
    private String surname;

    @Column(updatable = false, nullable = false)
    private LocalDate birthday;  // hibernate самостоятельно выберет тип DATE для базы данных

    @Column(nullable = false)
    private String gender;

    @Column(name = "registration_date", updatable = false, nullable = false)
    private LocalDate registrationDate;

    @Column(name = "address_id", nullable = false)
    private UUID addressId;

    // Метод вызывается перед сохранением новой сущности
    @PrePersist
    public void prePersist() {
        this.registrationDate = LocalDate.now();
    }

}
