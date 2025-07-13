package ru.shop.domain.service.supplier;

import org.springframework.stereotype.Service;
import ru.shop.dao.model.SupplierEntity;
import ru.shop.dao.repository.SupplierRepository;
import ru.shop.domain.model.AddressDto;
import ru.shop.domain.model.SupplierDto;
import ru.shop.domain.service.address.AddressService;
import ru.shop.mapper.SupplierMapper;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class SupplierServiceImpl implements SupplierService {

    private final SupplierRepository repository;
    private final SupplierMapper mapper;

    public SupplierServiceImpl(SupplierRepository supplierRepository) {
        this.repository = supplierRepository;
        this.mapper = new SupplierMapper();
    }

    @Override
    public boolean add(final SupplierDto supplier) {
        if (isValidDto(supplier)) {
            repository.save(mapper.toEntity(supplier, new SupplierEntity()));
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean changeAddress(final AddressService addressService,
                                 final UUID supplierId, final AddressDto newAddress) {
        Optional<SupplierEntity> supplierOpt = repository.findById(supplierId);
        if (supplierOpt.isPresent()) {
            if (addressService.add(newAddress)) {
                SupplierEntity supplier = supplierOpt.get();
                addressService.deleteById(supplier.getAddressId());
                supplier.setAddressId(newAddress.getId());
                repository.save(supplier);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean deleteById(final UUID id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public List<SupplierDto> getAll() {
        List<SupplierEntity> entities = repository.findAll();
        return entities.stream()
                .map(entity -> mapper.toDto(entity, new SupplierDto()))
                .collect(Collectors.toList());
    }

    @Override
    public SupplierDto getById(final UUID id) {
        Optional<SupplierEntity> supplier = repository.findById(id);
        return supplier.map(supplierEntity -> mapper
                .toDto(supplierEntity, new SupplierDto())).orElse(null);
    }

    private boolean isValidDto(final SupplierDto supplier) {
        return supplier.getId() != null && supplier.getName() != null &&
                supplier.getAddressId() != null && supplier.getPhoneNumber() != null;
    }
}
