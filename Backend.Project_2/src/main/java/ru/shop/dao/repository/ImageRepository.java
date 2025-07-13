package ru.shop.dao.repository;

import ru.shop.dao.model.ImageEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ImageRepository extends CrudRepository<ImageEntity, UUID> {
}
