package ru.shop.domain.service.image;

import org.springframework.stereotype.Service;
import ru.shop.domain.model.ImageDto;
import ru.shop.domain.service.product.ProductService;

import java.util.UUID;

@Service
public interface ImageService {

    /**
     * Добавление изображения.
     *
     * @param image Массив байт изображения.
     * @param id    Уникальный идентификатор изображения.
     * @return Успешность операции.
     */
    boolean add(final byte[] image, final UUID id);

    /**
     * Заменя изображения по уникальному идентификатору.
     *
     * @param newBytes  Новый массив байт изображения.
     * @param id        Уникальный идентификатор изображения.
     *                  Если не найден в базе данных, возвращается false.
     * @return Успешность операции.
     */
    boolean changeBytesById(final byte[] newBytes, final UUID id);

    /**
     * Удаление изображения по уникальному идентификатору.
     *
     * @param id Уникальный идентификатор.
     *           Если не найден в базе данных, возвращается false.
     * @return Успешность операции.
     */
    boolean deleteById(final UUID id);

    /**
     * Получение изображения по уникальному идентификатору.
     *
     * @param id Уникальный идентификатор.
     *           Если не найден в базе данных, возвращается null.
     * @return Объект изображения.
     */
    ImageDto getById(final UUID id);

    /**
     * Получение изображения по уникальному идентификатору товара.
     *
     * @param productId         Уникальный идентификатор товара.
     *                          Если не найден в базе данных, возвращается null.
     * @param productService    Сервис товаров.
     * @return Объект изображения.
     */
    ImageDto getByProductId(final UUID productId, final ProductService productService);
}
