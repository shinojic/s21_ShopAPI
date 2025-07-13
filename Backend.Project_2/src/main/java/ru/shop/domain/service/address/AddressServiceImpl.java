package ru.shop.domain.service.address;

import org.springframework.stereotype.Service;
import ru.shop.dao.model.AddressEntity;
import ru.shop.dao.repository.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import ru.shop.domain.model.AddressDto;

import java.util.UUID;

@Service
public class AddressServiceImpl implements AddressService {

    private final AddressRepository repository;

    @Autowired
    public AddressServiceImpl(AddressRepository repository) {
        this.repository = repository;
    }

    @Override
    public boolean add(final AddressDto address) {
        if (isValidDto(address)) {
            repository.save(new AddressEntity(
                    address.getId(),
                    address.getCountry(),
                    address.getCity(),
                    address.getStreet()));
            return true;
        } else {
            return false;
        }
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

    private boolean isValidDto(final AddressDto address) {
        return address.getId() != null &&
                address.getCountry() != null &&
                address.getCity() != null &&
                address.getStreet() != null;
    }

}
