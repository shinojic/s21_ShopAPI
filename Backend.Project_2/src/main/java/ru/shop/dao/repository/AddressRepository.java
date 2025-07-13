package ru.shop.dao.repository;

import ru.shop.dao.model.AddressEntity;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

@Repository
public interface AddressRepository extends CrudRepository<AddressEntity, UUID> {
}
