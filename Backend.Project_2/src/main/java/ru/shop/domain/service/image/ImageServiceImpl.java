package ru.shop.domain.service.image;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.shop.dao.model.ImageEntity;
import ru.shop.dao.repository.ImageRepository;
import ru.shop.domain.model.ImageDto;
import ru.shop.domain.model.ProductDto;
import ru.shop.domain.service.product.ProductService;
import ru.shop.mapper.ImageMapper;

import java.util.Optional;
import java.util.UUID;

@Service
public class ImageServiceImpl implements ImageService {

    private final ImageRepository repository;
    private final ImageMapper mapper;

    @Autowired
    public ImageServiceImpl(ImageRepository imageRepository) {
        this.repository = imageRepository;
        this.mapper = new ImageMapper();
    }

    public boolean add(final byte[] image, final UUID id) {
        ImageDto imageDto = new ImageDto(id, image);
        repository.save(mapper.toEntity(imageDto, new ImageEntity()));
        return true;
    }

    public boolean changeBytesById(final byte[] newBytes, final UUID id) {
        if (repository.existsById(id) && newBytes != null) {
            repository.save(new ImageEntity(id, newBytes));
            return true;
        } else {
            return false;
        }
    }

    public boolean deleteById(final UUID id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

    public ImageDto getById(final UUID id) {
        Optional<ImageEntity> image = repository.findById(id);
        return image.map(entity -> mapper
                .toDto(entity, new ImageDto())).orElse(null);
    }

    public ImageDto getByProductId(final UUID productId,
                                   final ProductService productService) {
        ProductDto product = productService.getById(productId);
        if (product != null) {
            UUID imageId = product.getImageId();
            return getById(imageId);
        } else {
            return null;
        }
    }
}
