package ru.shop.dao.repository;

import ru.shop.dao.model.ClientEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ClientRepository extends JpaRepository<ClientEntity, UUID> {

    List<ClientEntity> findByNameAndSurname(String name, String surname);

}
