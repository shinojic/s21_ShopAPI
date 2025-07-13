package ru.shop.domain.service.client;

import org.springframework.stereotype.Service;
import ru.shop.dao.model.ClientEntity;
import ru.shop.dao.repository.ClientRepository;
import ru.shop.mapper.ClientMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import ru.shop.domain.model.AddressDto;
import ru.shop.domain.model.ClientDto;
import ru.shop.domain.service.address.AddressService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ClientServiceImpl implements ClientService {

    private final ClientRepository repository;
    private final ClientMapper mapper;

    @Autowired
    public ClientServiceImpl(ClientRepository clientRepository) {
        this.repository = clientRepository;
        this.mapper = new ClientMapper();
    }

    @Override
    public boolean add(final ClientDto client) {
        if (isValidClientDto(client)) {
            repository.save(mapper.toEntity(client, new ClientEntity()));
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

    @Override
    public ClientDto getById(final UUID id) {
        Optional<ClientEntity> client = repository.findById(id);
        return client.map(clientEntity -> mapper
                .toDto(clientEntity, new ClientDto())).orElse(null);
    }

    @Override
    public List<ClientDto> getByNameAndSurname(final String name, final String surname) {
        List<ClientEntity> clients = repository.findByNameAndSurname(name, surname);
        return clients.stream()
                .map(entity -> mapper.toDto(entity, new ClientDto()))
                .collect(Collectors.toList());
    }

    @Override
    public List<ClientDto> getAll(final Integer limit,
                                  final Integer offset) throws IllegalArgumentException {
        List<ClientEntity> entities;
        if (limit == null && offset == null) {
            entities = repository.findAll();
        } else if ((limit != null && limit < 1) || (offset != null && offset < 1)) {
            throw new IllegalArgumentException("Limit and offset must be > 1");
        } else {
            int pageSize = Integer.MAX_VALUE;  // если limit null — берем очень большое число
            if (limit != null) {
                pageSize = limit;
            }
            int pageNumber = 0;
            if (offset != null) {
                pageNumber = offset / pageSize;  // для получения limit значений c offset позиции
            }
            Pageable pageable = PageRequest.of(pageNumber, pageSize);
            Page<ClientEntity> page = repository.findAll(pageable);
            entities = page.getContent();
        }
        return entities.stream()
                .map(entity -> mapper.toDto(entity, new ClientDto()))
                .collect(Collectors.toList());
    }

    @Override
    public boolean changeAddress(final AddressService addressService,
                                 final UUID clientId, final AddressDto newAddress) {
        Optional<ClientEntity> clientEntityOpt = repository.findById(clientId);
        if (clientEntityOpt.isPresent()) {
            if (addressService.add(newAddress)) {
                ClientEntity clientEntity = clientEntityOpt.get();
                addressService.deleteById(clientEntity.getAddressId());
                clientEntity.setAddressId(newAddress.getId());
                repository.save(clientEntity);
                return true;
            }
        }
        return false;
    }

    private boolean isValidClientDto(final ClientDto client) {
        return client.getId() != null &&
                client.getName() != null &&
                client.getSurname() != null &&
                client.getGender() != null &&
                client.getBirthday() != null &&
                client.getRegistrationDate() != null &&
                client.getAddressId() != null;
    }
}
