package ru.shop.domain.service.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.shop.dao.model.ProductEntity;
import ru.shop.dao.repository.ProductRepository;
import ru.shop.mapper.ProductMapper;
import ru.shop.domain.model.ProductDto;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository repository;
    private final ProductMapper mapper;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository) {
        this.repository = productRepository;
        this.mapper = new ProductMapper();
    }

    @Override
    public boolean add(final ProductDto product) {
        if (isValidProduct(product)) {
            repository.save(mapper.toEntity(product, new ProductEntity()));
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean reduceByAmount(final UUID id, final Integer amount) throws IllegalArgumentException {
        if (repository.existsById(id)) {
            ProductDto product = getById(id);
            if (amount > product.getAvailableStock() || amount < 1) {
                throw new IllegalArgumentException("amount must be > 0 and < availableStock");
            } else {
                product.setAvailableStock(product.getAvailableStock() - amount);
                repository.save(mapper.toEntity(product, new ProductEntity()));
                return true;
            }
        } else {
            return false;
        }
    }

    @Override
    public ProductDto getById(final UUID id) {
        Optional<ProductEntity> product = repository.findById(id);
        return product.map(entity -> mapper.toDto(entity, new ProductDto())).orElse(null);
    }

    @Override
    public List<ProductDto> getAll() {
        List<ProductEntity> entities = repository.findAll();
        return entities.stream()
                .map(entity -> mapper.toDto(entity, new ProductDto()))
                .collect(Collectors.toList());
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

    private boolean isValidProduct(final ProductDto product) {
        return product.getId() != null && product.getName() != null &&
                    product.getCategory() != null && product.getAvailableStock() != null
                    && product.getSupplierId() != null && product.getPrice() != null;
    }

}
