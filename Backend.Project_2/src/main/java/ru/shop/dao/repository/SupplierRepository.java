package ru.shop.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.shop.dao.model.SupplierEntity;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface SupplierRepository extends JpaRepository<SupplierEntity, UUID> {
}
